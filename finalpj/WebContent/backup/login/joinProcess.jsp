<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="javax.sql.*" %>
<%@page import="javax.naming.*" %>
<%@page import="java.sql.*" %>
 
<%
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
    
    pstmt=conn.prepareStatement("Insert into usertable values(?,?,?,?,?,?,?)");//커넥션풀 방식이다.
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
       out.println("<script>");
       out.println("location.href='loginForm.jsp'");
       out.println("</script>");
    }else {
       out.println("<script>");
       out.println("location.href='joinForm.jsp'");
       out.println("</script>");
    }
 }catch(Exception e){
    e.printStackTrace();    
    }
 session.setAttribute("id", id); //세션으로 받아와야 한다.
 response.sendRedirect("../top.jsp");
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
 