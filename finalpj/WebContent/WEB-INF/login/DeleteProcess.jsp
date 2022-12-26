<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
 

<%-- <%
	// 1. id값은 세션에서 얻습니다.
	String id = (String)session.getAttribute("user_id");
    
    	String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	String uid = "JSP";
	String upw = "JSP";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	String sql = "delete from members where id = ?";
	
	try{
		// 드라이버 호출
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// conn 생성
		conn = DriverManager.getConnection(url, uid, upw);
		// pstmt 생성
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		
		// sql 실행
		int result = pstmt.executeUpdate();
		
		if(result == 1){ // 성공
			session.invalidate(); // 세션에 정보삭제
			response.sendRedirect("login.jsp");			
		} else{ // 실패
			response.sendRedirect("login_welcome.jsp");
		}
		
	} catch(Exception e){
		e.printStackTrace();
		response.sendRedirect("login_welcome.jsp");
	} finally{
		try{
			if(conn != null) conn.close();
			if(pstmt != null) pstmt.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
%> --%>
