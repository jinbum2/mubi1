package action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import svc.ReviewService;
import utils.ConvertUtil;
import utils.ResponseUtil;
import vo.ActionForward;
import vo.CountInfo;
import vo.Review;

public class ReviewAction {//리뷰액션

    private ReviewService service;

    public void initAction() {
        service = new ReviewService();
    }

    private Review generateReviewByRequest(HttpServletRequest request) {

        String contents = request.getParameter("contents");
        int starPoint = ConvertUtil.convertString2Int(request.getParameter("starPoint"));
        int movieId = ConvertUtil.convertString2Int(request.getParameter("movieId"));
        int id = ConvertUtil.convertString2Int(request.getParameter("id"));

        HttpSession session = request.getSession();
        int userId = Integer.parseInt(session.getAttribute("userKey").toString());

        Review review = new Review();
        review.setContents(contents);
        review.setStarPoint(starPoint);
        review.setId(id);
        review.setMovieId(movieId);
        review.setUserId(userId);

        return review;
    }

    // 리뷰 등록 json
    public Action addReviewJson() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                
                service.addReview(generateReviewByRequest(request));

                PrintWriter writer = response.getWriter();
                writer.write(ResponseUtil.generateJSONResponse(true, null));
                writer.close();
                return null;
            }
        };
    }

    // 리뷰 삭제 json
    public Action removeReviewJson() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                
                service.removeReview(generateReviewByRequest(request));

                PrintWriter writer = response.getWriter();
                writer.write(ResponseUtil.generateJSONResponse(true, null));
                writer.close();
                return null;
            }
        };
    }

    // 리뷰 목록 조회 json
    public Action getReviewsByMovieIdJson() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                
                int movieId = ConvertUtil.convertString2Int(request.getParameter("movieId"));
                int pageNo = ConvertUtil.convertString2Int(request.getParameter("pageNo"));
                int pageSize = ConvertUtil.convertString2Int(request.getParameter("pageSize"));

                List<Review> reviews = service.getReviewsByMovieId(movieId, pageNo, pageSize);
                CountInfo countInfo = service.countReviewsByMovieId(movieId);

                PrintWriter writer = response.getWriter();
//                JSONObject json = new JSONObject();
                JSONArray jsonArr = new JSONArray();
                jsonArr.addAll(reviews);
//                json.put("list", jsonArr);
//                json.put("countInfo", countInfo);
                
                writer.write(ResponseUtil.generateJSONResponse(true, jsonArr));
                writer.close();
                return null;
            }
        };
    }
}
