<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="javax.sql.*" %>
<%@page import="javax.naming.*" %>
<%@page import="java.sql.*" %>
 
<%
// 이렇게 작업해두신 것이 있을거에요.
/*
 request.setCharacterEncoding("UTF-8");
 String id = request.getParameter("id");
 String pw = request.getParameter("pw");
 String name = request.getParameter("name");
 String birth = request.getParameter("birth");
 String email = request.getParameter("email");
 String tel = request.getParameter("tel");
 String address = request.getParameter("address");
	
Connection conn=null; //DB연결
 PreparedStatement pstmt=null; //SQL문
 ResultSet re = null; //해제
 
 try{
    Context init = new InitialContext();
    DataSource ds =(DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
    conn=ds.getConnection();
    
    pstmt=conn.prepareStatement("Insert into usertable (no,id,pw,name,birth,email,tel,address) values(seq_usertable.NEXTVAL, ?,?,?,?,?,?,?)");//커넥션풀 방식이다.
    pstmt.setString(1,id);
    pstmt.setString(2,pw);
    pstmt.setString(3,name);
    pstmt.setString(4,birth);
    pstmt.setString(5,email);
    pstmt.setString(6,tel);
    pstmt.setString(7,address);
     System.out.println("---------------------result------------------");
    int result = pstmt.executeUpdate();
    
    if(result!=0){
       out.println("<script>alert('가입되었습니다. 로그인하여주세요.');");
       out.println("location.href='loginForm'");
       out.println("</script>");
    }else {
       out.println("<script>");
       out.println("location.href='joinForm'");
       out.println("</script>");
    }
 }catch(Exception e){
    e.printStackTrace();    
    }
 */
 // 여긴 주석처리 되었으므로 기능하지 않습니다. 모든 처리가 잘 되었다면 mvc1 파일을 삭제합니다.
 // 가입후 로그인을 유도한다.
// session.setAttribute("id", id); //세션으로 받아와야 한다.
// response.sendRedirect("/");
 %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 
</body>
</html>
 