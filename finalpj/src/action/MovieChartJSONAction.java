package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MovieChartJSONService;
import vo.ActionForward;
import vo.MovieChart;

public class MovieChartJSONAction implements Action {//무비차트액션

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
		MovieChartJSONService movieChartJSONService = new MovieChartJSONService();
		ArrayList<MovieChart> movieCharts = movieChartJSONService.parsingKobisJSON(); // Chart목록을 파싱한다
		request.setAttribute("movieCharts", movieCharts); // parameter값 전달
		forward.setPath("/main.jsp");

		return forward;

	}
}
