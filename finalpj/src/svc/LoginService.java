package svc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class LoginService {

	// 가입 처리를 한다.//조인임
	public void join(HttpServletRequest request, HttpServletResponse response) 
			throws NumberFormatException, SQLException, NamingException {
		
//		request.setCharacterEncoding("UTF-8");
		// 위 구문은 필요하지 않습니다. 왜냐하면 여기는 jsp 가 아니라서요.
		// 위 구문류는 헷갈리니 다 가져왔는데 필요한가 고민을 해보시면 됩니다. 
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String birth = request.getParameter("birth");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");

		
		// 기본적으로 jsp 에서 임포트한 내역은 java 에도 존재합니다. 왜냐하면 실제로 jsp 는 사용자단에 보여질때
		// jsp -> class 로 번역되기 때문입니다. 이는 java -> class 와 동일합니다.
		Connection conn = null; // DB연결
		PreparedStatement pstmt = null; // SQL문
		ResultSet re = null; // 해제

		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();

			pstmt = conn.prepareStatement(
					"Insert into usertable (no,id,pw,name,birth,email,tel,address) values(seq_usertable.NEXTVAL, ?,?,?,?,?,?,?)");// 커넥션풀
																																	// 방식이다.
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, birth);
			pstmt.setString(5, email);
			pstmt.setString(6, tel);
			pstmt.setString(7, address);
			System.out.println("---------------------result------------------");
			int result = pstmt.executeUpdate();

			// jsp 내장 객체 session , request , out 이런것들이 쓰일때는 파라미터로 받아야합니다.
			// 특히 out 은 response 쪽에 PrintWriter 를 직접 열어서 써야 합니다.
			PrintWriter out = new PrintWriter(response.getOutputStream());
			// 상기 out instance 는 jsp 에서 편의상 자동으로 구현해주는 것이라 이런 것들만 신규로 만들어주면 됩니다.
			// 복붙했는데 에러나면 구글에서 jsp + 해당 객체명으로 조회할 수도 있습니다.
			
			if (result != 0) {
				out.println("<script>alert('가입되었습니다. 로그인해주세요.');");
				out.println("location.href='loginForm'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("location.href='joinForm'");
				out.println("</script>");
			}
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
