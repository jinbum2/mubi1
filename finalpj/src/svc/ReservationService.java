package svc;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import utils.ConnectionUtil;
import vo.CountInfo;
import vo.Movie;
import vo.Reservation;

public class ReservationService {//예약서비스

    private final ConnectionUtil<Reservation> connectionUtil;

    public ReservationService() {
        connectionUtil = new ConnectionUtil<Reservation>();
        connectionUtil.setModel(new Reservation());
    }

    // 예약을 한다. (자리 선점)
    public Reservation addReservation(Reservation reservation)
        throws Exception {

        verifyAccessReservationWhenUpdate(null, reservation);

        ArrayList<String> orderedParameters = new ArrayList<String>();
        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        orderedParameters.add(reservation.getMovieScreeningId() + "");
        orderedParameters.add(reservation.getUserId() + "");
        orderedParameters.add(reservation.getSeats());
        orderedParameters.add(today);
        orderedParameters.add(reservation.getPrice() + "");
        connectionUtil.save("INSERT INTO reservation (id, movie_screening_id,user_id,seats,reservated_at,canceled_at,price,deleted) "
        		+ "  values(seq_movie.NEXTVAL, ?, ?, ?, ?, null, ?, 0)",
//            connectionUtil.save("INSERT INTO reservation (movie_screening_id,user_id,seats,reservated_at,canceled_at,price,deleted) values(?, ?, ?, ?, null, ?, 0)",
            orderedParameters);
            
        // 방금 예약한 정보를 가져온다. 결제를 위해
        return connectionUtil.getOne("select * from reservation where movie_screening_id = ? and user_id = ? and seats = ? "
            + "and reservated_at = ? and deleted = 0 and paid = 0 and canceled_at is null and price = ? ",
            orderedParameters);
    }

    // 예약을 삭제한다.
    public void removeReservation(Reservation reservation)
        throws Exception {

        verifyAccessReservationWhenCancel(reservation);

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(reservation.getId() + "");
        connectionUtil.save("update reservation set deleted = 1 where id = ?",
            orderedParameters);
    }

    // 예약을 수정한다. (신규 예약 등록하고 기존 예약 삭제)
    public void modifyReservation(Reservation reservation)
        throws Exception {

        Reservation oldReservation = getReservation(reservation.getId());
        verifyAccessReservationWhenUpdate(oldReservation, reservation);

        addReservation(reservation);
        removeReservation(reservation);
    }

    // 예약을 취소한다.
    public void cancelReservation(Reservation reservation)
        throws Exception {

        verifyAccessReservationWhenCancel(reservation);

        ArrayList<String> orderedParameters = new ArrayList<String>();
        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        orderedParameters.add(today);
        orderedParameters.add(reservation.getId() + "");
        connectionUtil.save("update reservation set canceled_at = ? where id = ?",
            orderedParameters);
    }

    // 예약완료를 한다.
    public void completeReservation(Reservation reservation)
        throws Exception {

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(reservation.getId() + "");
        connectionUtil.save("update reservation set paid = 1 where id = ?",
            orderedParameters);
    }

    // 예약 내역을 조회한다.
    public Reservation getReservation(int id)
        throws NumberFormatException, SQLException, NamingException {

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(id + "");
        return connectionUtil.getOne("select * from reservation where deleted = 0 and id = ?",
            orderedParameters);
    }

    // 예약 내역을 조회한다. (목록)
    public List<Reservation> getReservationByUserId(int userId, int pageNo, int pageSize)
        throws NumberFormatException, SQLException, NamingException {

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(userId + "");
//        orderedParameters.add(pageSize + "");
//        orderedParameters.add(((pageNo - 1) * pageSize) + "");
        orderedParameters.add(((pageNo - 1) * pageSize + 1) + "");
        orderedParameters.add(((pageNo) * pageSize) + "");
        
        return connectionUtil.getList("select rs.* from "
            + "(select row_number() over (order by reservated_at desc) no, r.* from reservation r where r.user_id = ? and r.deleted = 0 order by r.reservated_at desc) rs "
            + "where rs.no between ? and ? ",
//            + "reservation where user_id = ? and deleted = 0 "
//            + "order by reservated_at desc limit ? offset ? ",
            orderedParameters);
    }
    
    // 목록 조회시 총 수량을 가져온다.
    public CountInfo countReservationByUserId(int userId)
        throws NumberFormatException, SQLException, NamingException {

        ConnectionUtil<CountInfo> countConnectionUtil = new ConnectionUtil<CountInfo>();
        countConnectionUtil.setModel(new CountInfo());
        
        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(userId + "");
        return countConnectionUtil.getOne("select count(*) as total_count from reservation where user_id = ? and deleted = 0 ",
            orderedParameters);
    }

    // 해당 상영정보(상영관|영화|시간)로 예약된 정보를 확인하여 이미 예약된 자리를 확인한다.
    public List<Reservation> getReservationByMovieScreeningId(int movieScreeningId)
        throws NumberFormatException, SQLException, NamingException {

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(movieScreeningId + "");
        return connectionUtil.getList(
            "select * from reservation where deleted = 0 and canceled_at is null and movie_screening_id = ? ",
            orderedParameters);
    }

    // 예약하려는 자리가 이미 예약되어 있는지 확인
    public boolean isPossibleReservation(int movieScreeningId, String seatLocation)
        throws NumberFormatException, SQLException, NamingException {

        // 디비에 이미 예약된(취소나 삭제 되지 않은) 정보를 가져온다.
        List<Reservation> reservations = getReservationByMovieScreeningId(movieScreeningId);

        for (Reservation reservation : reservations) {
            // 예약정보중 좌석번호값중 이번에 들어오는 좌석정보가 존재하면 예약 불가 false
            if(seatLocation.contains(",")) {
                // 배열인 경우
                String[] seatLocations = seatLocation.split(",");
                for (String location : seatLocations) {
                    if (reservation.getSeats().indexOf(location) > -1) {
                        return false;
                    }
                }
            } else {
                // 한건인 경우
                if (reservation.getSeats().indexOf(seatLocation) > -1) {
                    return false;
                }
            }
        }
        // 예약정보중 좌석번호를 모두 확인해도 이번에 들어오는 좌석정보가 존재하지 않으면 예약 가능 true
        return true;
    }

    private void verifyAccessReservationWhenCancel(Reservation reservation) throws Exception {

        Reservation oldReservation = getReservation(reservation.getId());
        if (oldReservation == null) {
            throw new Exception("예약 정보가 제대로 넘어오지 않았습니다.");
        }
        if (oldReservation.getUserId() != reservation.getUserId()) {
            throw new Exception("권한이 부족합니다. - 자신의 예약만 수정/삭제/취소가 가능합니다.");
        }
    }

    private void verifyAccessReservationWhenUpdate(Reservation oldReservation, Reservation newReservation)
        throws Exception {

        String seatLocation = newReservation.getSeats();
        if (seatLocation.contains(",")) {
            String[] seatLocations = seatLocation.split(",");
            for (String seat : seatLocations) {
                if (!isPossibleReservation(newReservation.getMovieScreeningId(), seat)) {
                    throw new Exception("예약이 불가능한 자리입니다. - 이미 다른 사람이 예약하였습니다.");
                }
            }
        } else {
            if (!isPossibleReservation(newReservation.getMovieScreeningId(), seatLocation)) {
                throw new Exception("예약이 불가능한 자리입니다. - 이미 다른 사람이 예약하였습니다.");
            }
        }
        if (oldReservation == null) {
            return;
        }
        if (newReservation == null) {
            throw new Exception("예약 정보가 제대로 넘어오지 않았습니다.");
        }
        if (oldReservation.getUserId() != newReservation.getUserId()) {
            throw new Exception("권한이 부족합니다. - 자신의 예약만 수정/삭제/취소가 가능합니다.");
        }
    }

    // 결제를 한다.
}
