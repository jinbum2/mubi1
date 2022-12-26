<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	request.setAttribute("top", "/WEB-INF/top.jsp");
	request.setAttribute("center", "/WEB-INF/movie/movieChart.jsp");
	request.setAttribute("bottom", "/WEB-INF/bottom.jsp");
%>
<jsp:forward page="/WEB-INF/template.jsp" />