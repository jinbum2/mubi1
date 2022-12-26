package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import action.MovieChartJSONAction;
import utils.ConvertUtil;
import vo.ActionForward;
import vo.Movie;

@WebServlet({ "/main", "", 
	"/insa" })
public class MainController extends AbsFrontHttpServlet {

    @Override
    protected Action doCommand(String command) throws Exception {
        if (command.equals("/main")) {//메인화면
            return new MovieChartJSONAction();
        }
        if (command.equals("/")) {//메인화면
            return new MovieChartJSONAction();
        }
        if (command.equals("/insa")) {
        	
        }
        return makeErrorAction();//에러화면
    }
}
