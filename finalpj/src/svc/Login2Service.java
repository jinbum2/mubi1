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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class Login2Service {
	
	
    public void login(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, NamingException {
    	String id = request.getParameter("id");//아이디를 로그인폼에서 받았어여
    	 String pass =request.getParameter("pw");//비밀번호를 받았어여
    	 
    	 Connection conn = null;//드라이브로딩을 위한 객체
    	 PreparedStatement pstmt =null;//// sql문 전송하는 객체인 PreparedStatement 
    	 ResultSet rs = null;//ResultSet이란 execteQuery의 명령을 반환하는 친구
    	 
    	 try{
    	  Context init=new InitialContext(); //커넥션 풀을 이용하게 되면 편리하게 디비연동을 할 수 있다.
    	  DataSource ds =(DataSource) init.lookup("java:comp/env/jdbc/OracleDB"); //아래서 저장한 connection name값을 불러온다.
    	  conn = ds.getConnection();
    	  
    	  pstmt=conn.prepareStatement("select * from usertable where id=?"); //prepareStatement는 Statement와 다르게 ?로 지정된 값을 필요 할 때 마다 이용할수있다.
    	  pstmt.setString(1,id);//컬렉션에서 문자열 값을 설정합니다
    	  rs=pstmt.executeQuery();//데이터베이스에서 데이터를 가져와서 결과 집합을 반환
    	  PrintWriter out = new PrintWriter(response.getOutputStream());//응답으로 내보낼 출력스트림을 뚫어준다
    	  
    	  if(rs.next()){//execteQuery의 결과가 있다면  
    	   if(pass.equals(rs.getString("pw"))){ //내가 친 패스워드랑 테이블에 패스워드 비교 후 같으면
    		   HttpSession session = request.getSession();//서버에 생성된 세션이 있다면 세션을 반환
    	       session.setAttribute("id",id);//id에 id값을 저장하고
    	       session.setAttribute("userKey", rs.getInt("no"));//유저키에 제가 테이블에 만든 시퀀스컬럼에 숫자로 저장한다
    	       //새션에 저장했으니까 세션값을 이밑에 메인 맵핑으로 보낸다
    	       
    	    out.println("<script>");
    	    out.println("location.href='/'"); // /(메인맵핑이에요)보낸다,메인컨트롤러에 있어요!
    	    out.println("</script>");
    	   }
    	  }
    	  out.println("<script>");
    	  out.println("location.href='/loginForm'");//틀리면 로그인폼보낸다 
    	  out.println("</script>");
    	  
    	  out.close();
    	 }catch(Exception e){
    	  e.printStackTrace();
    	 }
    }

}
