package vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//NOTICE: 영화 상영 테이블
public class MovieScreening extends ResultVO {
    private int id;                 // 식별자
    private int theaterNo;          // 상영관 번호
    private int movieId;            // 영화 식별자
    private String startedAt;       // 영화 시작시간
    private String endedAt;         // 영화 종료시간
    
    private List<Seat> seatLocation = new ArrayList<Seat>(); // 상영관별 좌석정보 - 해당 정보는 DB 에서 가져오지 않고 필요에 따라 서비스에서 넣어줍니다.
    private int currentSeatCount;                            // 상영관별 현재 남은 자리 - 해당 정보는 DB 에서 가져오지 않고 필요에 따라 서비스에서 넣어줍니다.
    private int maxSeatCount;                                // 상영관별 최대 자리 - 해당 정보는 DB 에서 가져오지 않고 필요에 따라 서비스에서 넣어줍니다.
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTheaterNo() {
        return theaterNo;
    }
    public void setTheaterNo(int theaterNo) {
        this.theaterNo = theaterNo;
    }
    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public String getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }
    public String getEndedAt() {
        return endedAt;
    }
    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }
    
    public int getCurrentSeatCount() {
        return currentSeatCount;
    }
    public void setCurrentSeatCount(int currentSeatCount) {
        this.currentSeatCount = currentSeatCount;
    }
    public int getMaxSeatCount() {
        return maxSeatCount;
    }
    public void setMaxSeatCount(int maxSeatCount) {
        this.maxSeatCount = maxSeatCount;
    }
    public List<Seat> getSeatLocation() {
        return seatLocation;
    }
    public void setSeatLocation(List<Seat> seatLocation) {
        this.seatLocation = seatLocation;
    }
    
    @Override
    public ResultVO generateByResultSet(ResultSet rs) throws SQLException {
        MovieScreening instance = new MovieScreening();
        instance.setId(rs.getInt("id"));
        instance.setTheaterNo(rs.getInt("theater_no"));
        instance.setMovieId(rs.getInt("movie_id"));
        instance.setStartedAt(rs.getString("started_at"));
        instance.setEndedAt(rs.getString("ended_at"));
        
        return instance;
    }
    @Override
    public String toString() {
        return "MovieScreening {id:" + id + ", theaterNo:" + theaterNo + ", movieId:" + movieId + ", startedAt:"
            + startedAt + ", endedAt:" + endedAt + ", seatLocation:" + seatLocation + ", currentSeatCount:"
            + currentSeatCount + ", maxSeatCount:" + maxSeatCount + "}";
    }
}
