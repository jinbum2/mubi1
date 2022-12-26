<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	String top = (String) request.getAttribute("top");
	String center = (String) request.getAttribute("center");
	String bottom = (String) request.getAttribute("bottom");
%>
<html>
<head>
<title>MovieSite</title>
</head>
<body>
	<div style="height: 20% auto;">
		<jsp:include page="<%=top%>" />
	</div>
	<div style="min-height: 72%;">
		<jsp:include page="<%=center%>" flush="false" />
	</div>
	<div style="height: 8% auto;">
		<jsp:include page="<%=bottom%>" />
	</div>
</body>
</html>