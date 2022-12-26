package controller;

import javax.servlet.annotation.WebServlet;

import action.Action;
import action.MovieAction;

@WebServlet({//영화
    "/movie", 
    "/movie/init", "/movie/list", "/movie/detail", 
    "/movie/list.ajax", "/movie/screenings.ajax"
})
public class MovieController extends AbsFrontHttpServlet {

    private final String PRE_WEB_PATH = "/movie";

    @Override
    protected Action doCommand(String command) throws Exception {
        MovieAction movieAction = new MovieAction();

        if (command.equals(PRE_WEB_PATH + "/init")) {//영화,상영관,db초기화
        	//영화정보,상영관정보,좌석정보를 가상에 만들어 db에 일괄 인서트하는 기능이다
        	// 초기화를 보시면 되요
            return movieAction.init();
        } else if (command.equals(PRE_WEB_PATH + "/list.ajax")) {//영화목록 json
        	// 그럼 여기 오게 되구요.
            return movieAction.getMovies(); // 이걸 실행하게 되죠.
            // 이렇게 디비 조회한 결과를 프린트하고 forward 가 null 인 액션을 리턴해요. action 은 널이 아니죠.
        } else if (command.equals(PRE_WEB_PATH + "/screenings.ajax")) {//상영관정보 json
            return movieAction.getMovieScreeningByMovieId();
        } else if (command.equals(PRE_WEB_PATH + "/list")) {//영화목록
            return movieAction.getMoviesPage();
        } else if (command.equals(PRE_WEB_PATH + "/detail")) {//영화상세
            return movieAction.getMoviePage();
        }
        return makeErrorAction();//에러화면
    }
}
