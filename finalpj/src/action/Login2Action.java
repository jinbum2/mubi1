package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.Login2Service;
import svc.MovieChartJSONService;
import svc.MovieService;
import vo.ActionForward;
import vo.MovieChart;

public class Login2Action {//애가진짜로그인액션임 ㅋ
	
	  private ActionForward forward;
	    private Login2Service service;

	    private final String FOLDER_PATH = "login/";

	    public void initAction() {
	        forward = new ActionForward();
	        service = new Login2Service();
	    }

	   
	    public Action login() throws Exception {
	        return new Action() {
	            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	                initAction();

	               
	                service.login(request, response);
	               // forward.setPath(FOLDER_PATH+"loginProcess.jsp");
	              //  forward.setRedirect(true);
	                return forward;
	            }
	        };
	    }

}
