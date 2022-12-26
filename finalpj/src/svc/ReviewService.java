package svc;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import utils.ConnectionUtil;
import vo.CountInfo;
import vo.Reservation;
import vo.Review;

public class ReviewService {//리뷰서비스

    private final ConnectionUtil<Review> connectionUtil;

    public ReviewService() {
        connectionUtil = new ConnectionUtil<Review>();
        connectionUtil.setModel(new Review());
    }

    // 리뷰를 등록한다.
    public void addReview(Review review)
        throws NumberFormatException, SQLException, NamingException {

        ArrayList<String> orderedParameters = new ArrayList<String>();
        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        orderedParameters.add(review.getUserId() + "");
        orderedParameters.add(review.getMovieId() + "");
        orderedParameters.add(review.getContents());
        orderedParameters.add(today);
        orderedParameters.add(review.getStarPoint() + "");
        connectionUtil.save("INSERT INTO review values(seq_review.NEXTVAL, ?, ?, ?, ?, ?)", orderedParameters);
//        connectionUtil.save("INSERT INTO review (user_id,movie_id,contents,created_at, star_point) values(?, ?, ?, ?, ?)", orderedParameters);
    }

    // 영화별 리뷰를 조회한다.
    public List<Review> getReviewsByMovieId(int movieId, int pageNo, int pageSize)
        throws NumberFormatException, SQLException, NamingException {

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(movieId + "");
//        orderedParameters.add(pageSize + "");
//        orderedParameters.add(((pageNo - 1) * pageSize) + "");
        orderedParameters.add(((pageNo - 1) * pageSize + 1) + "");
        orderedParameters.add(((pageNo) * pageSize) + "");
        return connectionUtil.getList("select rv.*, (select id from usertable where no = rv.user_id) as user_str_id from "
            + "(select row_number() over (order by r.created_at desc) no, r.* from review r where r.movie_id = ? order by r.created_at desc) rv "
            + "where rv.no between ? and ? order by rv.created_at desc ",
//            + "review r where movie_id = ? "
//            + "order by r.created_at desc limit ? offset ? ",
            orderedParameters);
    }
    
    // 목록 조회시 총 수량을 가져온다.
    public CountInfo countReviewsByMovieId(int movieId)
        throws NumberFormatException, SQLException, NamingException {

        ConnectionUtil<CountInfo> countConnectionUtil = new ConnectionUtil<CountInfo>();
        countConnectionUtil.setModel(new CountInfo());
        
        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(movieId + "");
        return countConnectionUtil.getOne("select count(*) as total_count from review where movie_id = ?",
            orderedParameters);
    }

    // 리뷰를 삭제한다.
    public void removeReview(Review review)
        throws NumberFormatException, SQLException, NamingException {

        ArrayList<String> orderedParameters = new ArrayList<String>();
        orderedParameters.add(review.getId() + "");
        connectionUtil.save("delete from review where id = ?", orderedParameters);
    }
}
