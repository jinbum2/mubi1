package controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import vo.ActionForward;

@WebServlet({
    "/error/404", "/error/401"
})
public class ErrorPageController extends AbsFrontHttpServlet {

    private final String PRE_WEB_PATH = "/error";

    @Override
    protected Action doCommand(String command) throws Exception {

        // 빈 화면을 생성한다. 대체로 url 을 잘못 타고 왔을때 보여준다.
        if (command.equals(PRE_WEB_PATH + "/404")) {
            return new Action() {
                public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
                    throws Exception {
                    ActionForward forward = new ActionForward();
                    forward.setPath("error/404.jsp");
                    return forward;
                }
            };
        }
        if (command.equals(PRE_WEB_PATH + "/401")) {
            return new Action() {
                public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
                    throws Exception {
                    ActionForward forward = new ActionForward();
                    forward.setPath("error/401.jsp");
                    return forward;
                }
            };
        }
        return null;
    }
}
