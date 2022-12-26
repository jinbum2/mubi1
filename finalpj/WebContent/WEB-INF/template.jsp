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
<style type="text/css">
	/*공통 스타일 js자리 */
	footer{
	
	}
</style>
</head>
<body>
	<header style="height: 20% auto;">
		<jsp:include page="<%=top%>" />
	</header>
	<section style="min-height: 72%;">
		<jsp:include page="<%=center%>" flush="false" />
	</section>
	<footer style="height: 8% auto;">
		<jsp:include page="<%=bottom%>" />
	</footer>
</body>
</html>