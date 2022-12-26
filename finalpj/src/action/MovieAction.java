package action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MovieChartJSONService;
import svc.MovieService;
import utils.ConvertUtil;
import utils.ResponseUtil;
import vo.ActionForward;
import vo.Movie;
import vo.MovieChart;
import vo.MovieScreening;
import vo.Reservation;

public class MovieAction {//무비액션

    private ActionForward forward;
    private MovieService service;

    private final String FOLDER_PATH = "movie/";

    public void initAction() {
        forward = new ActionForward();
        service = new MovieService();
    }

    // 최초 테스트 및 시연 데이터 세팅
    public Action init() throws Exception {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                MovieChartJSONService movieChartJSONService = new MovieChartJSONService();
                ArrayList<MovieChart> movieCharts = movieChartJSONService.parsingKobisJSON(); // Chart목록을 파싱한다
                // 외부 api를 파싱하는거 여기서 우리가 끌어올건 상영 영화 목록의 영화명이다
                service.init(movieCharts);
                request.setAttribute("movieCharts", movieCharts); // parameter값 전달
                forward.setPath("/");
                forward.setRedirect(true);
                return forward;
            }
        };
    }

    // 상영 영화 목록 조회
    public Action getMovies() throws Exception {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction(); // 요건 포워드 초기화, 서비스 초기화구요

                PrintWriter writer = response.getWriter();
                writer.write(ResponseUtil.generateJSONResponse(true, service.getMovies())); // 여기요
                writer.close();
                return null; // 포워드를 지정하지 않았어요. 이러면 내가 원하는 것을 직접 print 하고 끝내요.
            }
        };
    }

    // 상영 영화중 상영관 정보 조회
    public Action getMovieScreeningByMovieId()
        throws Exception {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                int movieId = ConvertUtil.convertString2Int(request.getParameter("movieId"));

                PrintWriter writer = response.getWriter();
                writer.write(ResponseUtil.generateJSONResponse(true, service.getMovieScreeningByMovieId(movieId)));
                writer.close();
                return null;
            }
        };
    }

    // 영화 목록 페이지로 이동
    public Action getMoviesPage() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                request.setAttribute("movies", service.getMovies());
                forward.setPath(FOLDER_PATH + "list.jsp");
                return forward;
            }
        };
    }

    // 영화 상세 페이지로 이동
    public Action getMoviePage() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                int id = ConvertUtil.convertString2Int(request.getParameter("id"));

                // 예약된 영화 데이터
                Movie movie = service.getMovieById(id);
                request.setAttribute("movie", movie);

                HttpSession session = request.getSession();
                request.setAttribute("userKey", session.getAttribute("userKey"));

                forward.setPath(FOLDER_PATH + "detail.jsp");
                return forward;
            }
        };
    }
}
