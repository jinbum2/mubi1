package vo;

import java.sql.ResultSet;
import java.sql.SQLException;

// NOTICE: 예약 테이블
public class Reservation extends ResultVO {
    private int id;                 // 식별자
    private int movieScreeningId;   // 영화 상영 식별자
    private int userId;             // 사용자 식별자
    private String seats;           // 선택한 좌석 목록 seperator [,]
    private String reservatedAt;    // 예약한 시간
    private String cancelAt;        // 예약취소한 시간
    private int price;              // 구매 가격
    private int paid;               // 지불여부
    private int deleted;            // 삭제 여부
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMovieScreeningId() {
        return movieScreeningId;
    }
    public void setMovieScreeningId(int movieScreeningId) {
        this.movieScreeningId = movieScreeningId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getSeats() {
        return seats;
    }
    public void setSeats(String seats) {
        this.seats = seats;
    }
    public String getReservatedAt() {
        return reservatedAt;
    }
    public void setReservatedAt(String reservatedAt) {
        this.reservatedAt = reservatedAt;
    }
    public String getCancelAt() {
        return cancelAt;
    }
    public void setCancelAt(String cancelAt) {
        this.cancelAt = cancelAt;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPaid() {
        return paid;
    }
    public void setPaid(int paid) {
        this.paid = paid;
    }
    public int getDeleted() {
        return deleted;
    }
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
    
    @Override
    public ResultVO generateByResultSet(ResultSet rs) throws SQLException {
        Reservation instance = new Reservation();
        instance.setId(rs.getInt("id"));
        instance.setMovieScreeningId(rs.getInt("movie_screening_id"));
        instance.setUserId(rs.getInt("user_id"));
        
        instance.setSeats(rs.getString("seats"));
        instance.setReservatedAt(rs.getString("reservated_at"));
        instance.setCancelAt(rs.getString("canceled_at"));

        instance.setPrice(rs.getInt("price"));
        instance.setPaid(rs.getInt("paid"));
        instance.setDeleted(rs.getInt("deleted"));
        
        return instance;
    }
    @Override
    public String toString() {
        return "Reservation {id:" + id + ", movieScreeningId:" + movieScreeningId + ", userId:" + userId + ", seats:"
            + seats + ", reservatedAt:" + reservatedAt + ", cancelAt:" + cancelAt + ", price:" + price + ", paid:"
            + paid + ", deleted:" + deleted + "}";
    }
}
