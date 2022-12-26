package controller;

import javax.servlet.annotation.WebServlet;

import action.Action;
import action.LoginAction;
/*
// 자동 상속을 쓰면 아래 커맨드만 함수로 나와요//조인임
public class LoginController extends AbsFrontHttpServlet {//회원가입
	
	private final LoginAction loginAction;
	
	public LoginController() {
		this.loginAction = new LoginAction();
	}

	@Override
	protected Action doCommand(String command) throws Exception {
		// TODO Auto-generated method stub
		if (command.equals("/joinProcess")) {// 가입
        	// 이런식으로 해두고 액션을 구현합니다.
            return loginAction.join();
            // 역순으로 개발해도 됩니다.
        }
		return null;
	}
}
*/