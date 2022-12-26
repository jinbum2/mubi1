<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	request.setAttribute("top", "/top.jsp");
	request.setAttribute("center", "/movie/movieChart.jsp");
	request.setAttribute("bottom", "/bottom.jsp");
%>
<jsp:forward page="/template.jsp" />