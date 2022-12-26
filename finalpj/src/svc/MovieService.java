package svc;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import utils.ConnectionUtil;
import vo.Movie;
import vo.MovieChart;
import vo.MovieScreening;
import vo.Reservation;
import vo.Seat;

public class MovieService {

    // 영화 목록을 가져온다.
    public List<Movie> getMovies() throws NumberFormatException, SQLException, NamingException {
        ConnectionUtil<Movie> connectionUtil = new ConnectionUtil<Movie>();
        connectionUtil.setModel(new Movie());
        // 여기가 쿼리에요. 기본적으로 service 는 일련의 작업을 처리해요. 
        // 디비 조회를 한다거나 외부 api 를 끌어다 쓴다던가 등등이요.
        List<Movie> movies = connectionUtil
            .getList("select m.*, (select avg(star_point) from review where movie_id = m.id) as star_point_avg "
                + "from movie m order by m.id desc", new ArrayList<String>());
        return movies;
    }

    // 영화 가져온다.
    public Movie getMovieById(int movieId) throws NumberFormatException, SQLException, NamingException {
        ConnectionUtil<Movie> connectionUtil = new ConnectionUtil<Movie>();
        connectionUtil.setModel(new Movie());

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(movieId + "");
        Movie movie = connectionUtil
            .getOne("select m.*, (select avg(star_point) from review where movie_id = m.id) as star_point_avg from movie m where m.id = ?", orderedParameters);
        return movie;
    }

    // 상영정보 가져온다.
    public MovieScreening getMovieScreeningById(int movieScreeningById)
        throws NumberFormatException, SQLException, NamingException {
        ConnectionUtil<MovieScreening> connectionUtil = new ConnectionUtil<MovieScreening>();
        connectionUtil.setModel(new MovieScreening());

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(movieScreeningById + "");
        MovieScreening movie = connectionUtil
            .getOne("select * from movie_screening where id = ?", orderedParameters);
        return movie;
    }

    // 상영 영화중 상영관 정보 조회 
    public List<MovieScreening> getMovieScreeningByMovieId(int movieId)
        throws NumberFormatException, SQLException, NamingException {

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(movieId + "");

        List<MovieScreening> movieScreenings;
        // 현재 시간 이후 정보만 조회
        {
            ConnectionUtil<MovieScreening> connectionUtil = new ConnectionUtil<MovieScreening>();
            connectionUtil.setModel(new MovieScreening());

            String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            orderedParameters.add(today);
            movieScreenings = connectionUtil.getList("select * from movie_screening "
                + " where movie_id = ? and started_at > ? order by started_at", orderedParameters);
        }

        // 각 상영관별 자리 정보를 가져옵니다.
        {
            if (movieScreenings == null) {
                return movieScreenings;
            }

            ConnectionUtil<Seat> connectionUtil = new ConnectionUtil<Seat>();
            connectionUtil.setModel(new Seat());

            for (MovieScreening movieScreening : movieScreenings) {
                orderedParameters.clear();

                orderedParameters.add(movieScreening.getTheaterNo() + "");
                List<Seat> seats = connectionUtil.getList("select * from seat "
                    + " where theater_no = ? ", orderedParameters);

                movieScreening.setMaxSeatCount(seats.size()); // 최대 자리 저장
                movieScreening.getSeatLocation().addAll(seats); // 좌석 정보 저장
                int reservatedSeatCount = 0;

                ReservationService reservationService = new ReservationService();
                List<Reservation> reservations = reservationService
                    .getReservationByMovieScreeningId(movieScreening.getId());
                for (Reservation reservation : reservations) {
                    if (reservation.getSeats().contains(",")) {
                        // 여러 좌석을 구매한 경우
                        String[] seatLocations = reservation.getSeats().split(",");

                        for (String seatLocation : seatLocations) {
                            for (Seat seat : seats) {
                                if (seatLocation.equals(seat.getLocation())) {
                                    seat.setPossibleReservated(false);
                                    reservatedSeatCount++;
                                }
                            }
                        }

                    } else {
                        // 좌석을 하나만 구매한 경우
                        for (Seat seat : seats) {
                            if (reservation.getSeats().equals(seat.getLocation())) {
                                seat.setPossibleReservated(false);
                                reservatedSeatCount++;
                            }
                        }
                    }
                }
                movieScreening.setCurrentSeatCount(seats.size() - reservatedSeatCount); // 남은 좌석 수 = 총 좌석수 - 예약 좌석
            }
        }
        return movieScreenings;
    }

    // 영화 데이터 최초 세팅&저장
    public void init(ArrayList<MovieChart> movieCharts)
        throws NumberFormatException, SQLException, NamingException {

        ConnectionUtil<Movie> connectionUtil = new ConnectionUtil<Movie>();

        // 영화를 지우고 외부계 차트를 기준으로 영화를 등록한다.
        connectionUtil.save("delete from movie", new ArrayList<String>());

        for (MovieChart movieChart : movieCharts) {
        	if(movieChart.getImage() == null) {
        		// 외부계 api : 영화 목록 가져올때 이미지가 없으면 등록하지 않는다.
        		continue;
        	}

            ArrayList<String> orderedParameters = new ArrayList<String>();
            orderedParameters.add(movieChart.getMovieNm());
            orderedParameters.add(movieChart.getImage()); // null 이 내려옴??
            orderedParameters.add("10000");
            // 이야기 하신대로 그걸 그대로 insert 에 넣어줘요. 그래서 몇개를 가져오던 다 나오죠
            connectionUtil.save("INSERT INTO movie values(seq_movie.NEXTVAL, ?, ?, ?)", orderedParameters);
//            connectionUtil.save("INSERT INTO movie (title, image, price) values(?, ?, ?)", orderedParameters);
        }

        // 영화 목록이 등록되면 상영관 정보를 재등록한다.
        connectionUtil.save("delete from seat", new ArrayList<String>());

        int maxCountTheater = 3; // 최대 상영관 수
        int maxCountCols = 10; // 상영관 내 열갯수
        int maxCountRows = 8; // 상영관 내 행갯수
        for (int inx = 0; inx < maxCountTheater; inx++) {
            for (int jnx = 0; jnx < maxCountRows; jnx++) {
                for (int knx = 0; knx < maxCountCols; knx++) {
                    ArrayList<String> orderedParameters = new ArrayList<String>();
                    orderedParameters.add((inx + 1) + "");
                    orderedParameters.add(((char)('A' + jnx)) + "" + knx);
                    System.out.println(((char)('A' + jnx)) + "" + knx);
                    connectionUtil.save("INSERT INTO seat values(seq_seat.NEXTVAL, ?, ?)", orderedParameters);
//                    connectionUtil.save("INSERT INTO seat (theater_no, location) values(?, ?)", orderedParameters);
                }
            }
        }

        // 상영관별 영화를 등록한다. 하루에 각 상영관에서 5편정도 영화를 돌아가면서 보여준다. 2시간씩 틀어주며, 30분의 휴계시간을 갖는다.
        // 할인은 적용하지 않는다. 
        connectionUtil.save("delete from movie_screening", new ArrayList<String>());
        connectionUtil.setModel(new Movie());
        List<Movie> moviesInDB = connectionUtil.getList("select m.*, (select avg(star_point) from review where movie_id = m.id) as star_point_avg from movie m order by m.id desc",
            new ArrayList<String>());

        int maxCountDate = 14; // 몇일치 상영 데이터를 넣을지 결정
        for (int knx = 0; knx < maxCountDate; knx++) {
            List<Movie> movies = new ArrayList<Movie>();
            movies.addAll(moviesInDB);

            Date targetDate = new Date();
            targetDate.setDate(targetDate.getDate() + knx);
            String today = new SimpleDateFormat("yyyy-MM-dd").format(targetDate);
            int minute = 0;

            for (int inx = 0; inx < maxCountTheater; inx++) {
                List<Integer> preMovieq = new ArrayList<Integer>();

                for (int jnx = 9; jnx < 20; jnx = jnx + 2) {

                    // 랜덤으로 상영할 영화를 꼽음
                    int movieq = (int)(Math.round(Math.random() * 1000) % movies.size());

                    // 중복 제거 최대 10회 : 10회 돌렸는데 계속 있는 값이면 그냥 받아들인다.
                    int tc = 1;
                    while (preMovieq.contains(movieq)) {
                        if (tc > 10)
                            break;
                        movieq = (int)(Math.round(Math.random() * 1000) % movies.size());
                        tc++;
                    }
                    String startTime = (jnx < 10 ? "0" + jnx : jnx) + ":" + (minute < 10 ? "00" : "30");
                    String endTime = (jnx + 2 < 10 ? "0" + (jnx + 2) : jnx + 2) + ":" + (minute < 10 ? "30" : "00");

                    ArrayList<String> orderedParameters = new ArrayList<String>();
                    orderedParameters.add((inx + 1) + "");
                    orderedParameters.add(movies.get(movieq).getId() + "");
                    orderedParameters.add(today + " " + startTime);
                    orderedParameters.add(today + " " + endTime);

                    connectionUtil.save(
                        "INSERT INTO movie_screening values(seq_movie_screening.NEXTVAL, ?, ?, ?, ?)",
//                        "INSERT INTO movie_screening (theater_no,movie_id,started_at,ended_at) values(?, ?, ?, ?)",
                        orderedParameters);

                    if (minute == 30) {
                        jnx = jnx + 1;
                        minute = 0;
                    } else {
                        minute = 30;
                    }
                }
            }
        }

    }
}
