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
        id = (String) session.getAttribute("id");//�α����� ���������� �����ϸ� ����â ����.

	%>
	<b><%=id%></b>�� ȯ�� �մϴ�. <a href="logout">�α׾ƿ�</a>
	<%
		} else {
	%>
	<a href="login\loginForm.jsp">�α���</a> | 
	<a href="login\joinForm.jsp">ȸ������</a>
	<%
		}
	%>
	&nbsp;&nbsp;&nbsp;
</div>

<form action="Tiket.jsp" method="post" align="center">
		<input type="submit" style="width:60pt;height:60pt;background: yellow;font-size:25pt;" value="����">
</form>
<form action="index.jsp" method="post" align="center">
		<input type="submit" " value="�󿵽ð�ǥ">
</form>


