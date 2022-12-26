package vo;

import java.sql.ResultSet;
import java.sql.SQLException;

//NOTICE: 리뷰 테이블
public class Review extends ResultVO {
    private int id;                 // 식별자
    private int userId;             // 사용자 식별자
    private int movieId;            // 영화 식별자
    private int starPoint;          // 별점
    private String contents;        // 리뷰 내용
    private String createdAt;       // 작성일시
    private String userStrId;        // 사용자 식별 아이디
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStarPoint() {
        return starPoint;
    }

    public void setStarPoint(int starPoint) {
        this.starPoint = starPoint;
    }

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    @Override
    public ResultVO generateByResultSet(ResultSet rs) throws SQLException {
        Review instance = new Review();
        instance.setId(rs.getInt("id"));
        instance.setUserId(rs.getInt("user_id"));
        instance.setMovieId(rs.getInt("movie_id"));
        instance.setContents(rs.getString("contents"));
        instance.setCreatedAt(rs.getString("created_at"));
        instance.setStarPoint(rs.getInt("star_point"));
        
        instance.setUserStrId(rs.getString("user_str_id"));
        
        return instance;
    }

    @Override
    public String toString() {
        return "Review {id:" + id + ", userId:" + userId + ", movieId:" + movieId + ", starPoint:" + starPoint
            + ", contents:" + contents + ", createdAt:" + createdAt + "}";
    }
    
}
