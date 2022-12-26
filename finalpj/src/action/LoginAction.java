package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.LoginService;
import svc.MovieChartJSONService;
import svc.MovieService;
import vo.ActionForward;
import vo.MovieChart;

// 액션은 액션 클래스를 받아도 되고, 액션을 리턴하는 함수를 만들어도 됩니다.//조인임
public class LoginAction {//애는 회원가입액션임 ㅋ

    private ActionForward forward;
    private LoginService service;

    private final String FOLDER_PATH = "login/"; // 실제 WEB-INF 밑 폴더를 기재합니다. WEB-INF는 AbsFrontHttpServlet 에서
    // 붙여줍니다.

    public void initAction() {
        forward = new ActionForward();
        service = new LoginService(); // 아직 구현하지 않은 서비스입니다. 예가 가입 함수를 가질 것입니다.
    }

    // 가입
    public Action join() throws Exception {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                
                

                service.join(request, response); // response 를 넣어줍니다.
//                forward.setPath(FOLDER_PATH + "joinProcess.jsp"); // 불필요하지만 다른것에 쓰일까봐 적습니다.
//                forward.setRedirect(true); // 사실 리디렉션을 하니 페이지 path 가 논리적으로 필요하진 않습니다.
                // 응답이 이미 커밋된 후에는 forward할 수 없습니다. --> 이미 out.print 를 서블릿에서 했기 때문에
                // forward 가 안먹힌 거에요.
                return null; // 이렇게 막아줍니다.
            }
        };
    }
}
