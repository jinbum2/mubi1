package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import vo.ActionForward;

// HttpServlet 또는 그 구현체인 AbsFrontHttpServlet 를 하나 상속 받습니다.
// HttpServlet 을 사용하는 추상클래스다. 기존 기능이 모든 서블릿이 사용하는 것이므로 부모가 갖는다, 서로 성질이 달라 재구현을 해야하는 기능만 (doCommand) 함수로 뽑았다.

//.do 같은 뒤 표기를 안쓴 것은 최신  REST URL 개발 나오면서 시작했대..이제 .do안쓴대..
@SuppressWarnings("serial")
public abstract class AbsFrontHttpServlet extends HttpServlet {
        
    private final String ROOT_WEB_FOLDER_PATH = "/WEB-INF/";

    // 이미 알고계시겠지만, abstract 는 추상 클래스에요. 예는 추상 메소드. 반드시 상속 해서 재구현 해야하는 메서드에요
    // 
    abstract protected Action doCommand(String command) throws Exception;
    
    private void doProcess(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String RequestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = RequestURI.substring(contextPath.length());
        ActionForward forward = null;

        System.out.println(command);
        try {
        	// doProcess 의 생명주기상 반드시 doCommand 를 실행하게끔 해요. 
        	// 그러나 class 의 다형성에 따라 각 자식들은 doCommand 가 다 다르죠.
        	// 어쨋든 그렇게 forward 가 없는 액션이 리턴되구요
            Action action = doCommand(command);
            if (action == null)
                return;
            forward = action.execute(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // forward 가 없기 때문에 여길 스킵합니다.
        // 단 쓰일때 response 에서 out.print 정의를 했으므로 출력물은 있게 되죠.
        if (forward != null) {
            if (forward.isRedirect()) {
                response.sendRedirect(forward.getPath());
            } else {
            	// WEB-INF 는 여기서 자동으로 붙여주기 때문입니다.
                RequestDispatcher dispatcher = request.getRequestDispatcher(ROOT_WEB_FOLDER_PATH + forward.getPath());
                dispatcher.forward(request, response);
            }
        }
    }

    // 404로 이동합니다.
    protected Action makeErrorAction() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                ActionForward forward = new ActionForward();
                forward.setPath("/error/404");
                forward.setRedirect(true);
                return forward;
            }
        };
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        doProcess(request, response);
    }
}