<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("euc-kr");
	
%>
<div align="center" style="padding: 50px">
<h1>MUBI</h1>
</div> 
<div align="right" style="padding: 10px">
	<%String id = null;
    if (session.getAttribute("id") != null) {
        id = (String) session.getAttribute("id");//로그인을 성공적으로 수행하면 메인창 입장.

	%>
	<b><%=id%></b>님 환영 합니다. <a href="logout">로그아웃</a>
	<%
		} else {
	%>
	<a href="login\loginForm.jsp">로그인</a> | 
	<a href="login\joinForm.jsp">회원가입</a>
	<%
		}
	%>
	&nbsp;&nbsp;&nbsp;
</div>

<form action="Tiket.jsp" method="post" align="center">
		<input type="submit" style="width:60pt;height:60pt;background: yellow;font-size:25pt;" value="예매">
</form>
<form action="index.jsp" method="post" align="center">
		<input type="submit" " value="상영시간표">
</form>


