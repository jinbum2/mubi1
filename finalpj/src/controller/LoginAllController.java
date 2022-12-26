package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import action.Login2Action;
import action.LoginAction;
import vo.ActionForward;


@WebServlet({
    "/joinProcess",
    "/loginProcess",
    "/loginForm",
    "/joinForm",
    "/logout"
})

public class LoginAllController extends AbsFrontHttpServlet {
	
	private final Login2Action login2Action;
	
	private final LoginAction loginAction;
	
	public LoginAllController() {
		this.login2Action = new Login2Action();
		this.loginAction = new LoginAction();
	}

	@Override
	protected Action doCommand(String command) throws Exception {
		// TODO Auto-generated method stub

        if (command.equals("/loginForm")) {//로그인폼
        	// interface 를 이렇게 즉시 구현하는 방식이 있구요, 클래스화 하는 방식이 있는데요.
        	// 어차피 런타임에서는 둘다 같아요
            return new Action() {
                public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                    ActionForward forward = new ActionForward();
                    forward.setPath("login/loginForm.jsp");
                    return forward;
                }
            };
        }
       
        if (command.equals("/loginProcess")) {//로그인 프로세스
            return login2Action.login(); // ?
        }
        if (command.equals("/joinProcess")) {//조인프로세스
            return loginAction.join();
        }
        // 겹치는 url 을 없애줍니다. -> 매핑 url 에러가 발생하지 않도록
        if (command.equals("/joinForm")) {//조인폼
            return new Action() {
                public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                    ActionForward forward = new ActionForward();
                    forward.setPath("login/joinForm.jsp");
                    return forward;
                }
            };
        }
        
        if(command.equals("/logout")) {
        	return new Action() {
				public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
						throws Exception {
        			HttpSession session = request.getSession();
        			session.invalidate();
        			
					ActionForward forward = new ActionForward();
                    forward.setPath("/");
                    forward.setRedirect(true);
                    return forward;
				}
        	};
        }
        
        if (command.equals("/deleteForm")) {//딜리트폼
        	
            return new Action() {
                public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                    ActionForward forward = new ActionForward();
                    forward.setPath("login/deleteForm.jsp");
                    return forward;
                }
            };
        }
        
        if (command.equals("/updateForm")) {//업데이트폼
        	
            return new Action() {
                public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                    ActionForward forward = new ActionForward();
                    forward.setPath("login/updateForm.jsp");
                    return forward;
                }
            };
        }
		return null;
	}

}
