package controller;

import javax.servlet.annotation.WebServlet;

import action.Action;
import action.ReviewAction;

@WebServlet({//리뷰컨트롤러
    "/review", 
    "/review/list.ajax",
    "/review/remove.ajax",
    "/review/add.ajax"
})
public class ReviewController extends AbsFrontHttpServlet {//리뷰 컨트롤러
    
    private final String PRE_WEB_PATH = "/review";

    @Override
    protected Action doCommand(String command) throws Exception {
        ReviewAction reviewAction = new ReviewAction();

        if (command.equals(PRE_WEB_PATH + "/list.ajax")) {//리뷰목록 조회
            return reviewAction.getReviewsByMovieIdJson();
        } else if (command.equals(PRE_WEB_PATH + "/remove.ajax")) {//리뷰단건 삭제
            return reviewAction.removeReviewJson();
        } else if (command.equals(PRE_WEB_PATH + "/add.ajax")) {//리뷰한거 등록
            return reviewAction.addReviewJson();
        }
        return makeErrorAction();
    }
}
