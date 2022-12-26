package vo;

import java.sql.ResultSet;
import java.sql.SQLException;

// 영화 테이블
public class Movie extends ResultVO {
    private int id;                 // 식별자
    private String title;           // 영화 제목
    private String image;           // 영화 타이틀 이미지
    private int price;              // 가격
    private String starPointAvg;              // 별점 평점
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    public String getStarPointAvg() {
        return starPointAvg;
    }
    public void setStarPointAvg(String starPointAvg) {
        this.starPointAvg = starPointAvg;
    }
    
    @Override
    public ResultVO generateByResultSet(ResultSet rs) throws SQLException {
        Movie instance = new Movie();
        instance.setId(rs.getInt("id"));
        instance.setTitle(rs.getString("title"));
        instance.setImage(rs.getString("image"));
        instance.setPrice(rs.getInt("price"));
        instance.setStarPointAvg(rs.getString("star_point_avg"));
        
        return instance;
    }
    
    @Override
    public String toString() {
        return "Movie {id:" + id + ", title:" + title + ", price:" + price + ", starPointAvg:" + starPointAvg + "}";
    }
}
