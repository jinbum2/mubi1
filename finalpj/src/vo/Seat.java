package vo;

import java.sql.ResultSet;
import java.sql.SQLException;

//NOTICE: 좌석 테이블
public class Seat extends ResultVO {
    private int id;                 // 식별자
    private int theaterNo;          // 상영관 번호
    private String location;        // 좌석 위치
    
    private boolean isPossibleReservated = true;          // 예약 가능 여부 - 해당 정보는 DB 에서 가져오지 않고 필요에 따라 서비스에서 넣어줍니다.
    
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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    public boolean isPossibleReservated() {
        return isPossibleReservated;
    }
    public void setPossibleReservated(boolean isPossibleReservated) {
        this.isPossibleReservated = isPossibleReservated;
    }
    @Override
    public ResultVO generateByResultSet(ResultSet rs) throws SQLException {
        Seat instance = new Seat();
        instance.setId(rs.getInt("id"));
        instance.setTheaterNo(rs.getInt("theater_no"));
        instance.setLocation(rs.getString("location"));
        
        return instance;
    }
    @Override
    public String toString() {
        return "Seat {id:" + id + ", theaterNo:" + theaterNo + ", location:" + location + ", isPossibleReservated:"
            + isPossibleReservated + "}";
    }
}
