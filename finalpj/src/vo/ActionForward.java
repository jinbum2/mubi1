package vo;

public class ActionForward {
	/*
     * 서블릿에서 클라이언트로부터 요청을 전달받아 처리한 후
     * 지정한 View 페이지로 포워딩할 때
     * 포워딩 할 View 페이지의 주소(URL)와 포워딩 방식(Redirect  or  Dispatch) 을 
     * 공통으로 다루기 위한 클래스
     */
	private boolean isRedirect = false;//포워딩 방식 저장할 변수
	private String path = null;//포워딩 주소 저장할 변수
	//true : Redirect 방식, false : Dispatch 방식
	
	public boolean isRedirect() {
		return isRedirect;
	}

	public String getPath() {
		return path;
	}

	public void setRedirect(boolean b) {
		isRedirect = b;
	}

	public void setPath(String string) {
		path = string;
	}
}