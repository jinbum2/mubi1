<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("euc-kr");
	
%>

 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<style>
	.loginBox{
	    display: flex;
    justify-content: flex-end;
    /*�µθ�ä���� */
    /* background:#000; */ 
    border: 3px solid #eee;
    padding: 8px;
	}
    form {
    display: block;
    margin:5px;
    margin-top: 0em;
    margin-block-end: 0em !important;
    }
    .tiketing{
    display: flex;
    justify-content: flex-end;
    padding: 8px;
 	}
</style>
<div class="container">
<div align="center" >
<h1>MUBI</h1>
</div> 
<div align="right" >
	<%String id = null;
    if (session.getAttribute("id") != null) {
        id = (String) session.getAttribute("id");//�α����� ���������� �����ϸ� ����â ����.

	%>
	<%-- <b><%=id%></b>�� ȯ�� �մϴ�. --%>
	<div class="container loginBox">
	<!-- <a href="deleteForm">ȸ��Ż��</a> -->
	<!-- <a href="updateForm">ȸ����������</a>-->
	<a href="logout">�α׾ƿ�</a>
	</div>
	<%
		} else {
	%>
	<div class="container loginBox">
	<form action="loginForm"method="post" >
	<input type="submit" value="�α���" class="btn btn-secondary">
	</form> 
	<form action="joinForm"method="post" >
	<input type="submit" value="ȸ������" class="btn btn-warning">
	</form>
	</div>
	<%
		}
	%>
	
	
	
	<!-- <a href="loginForm">�α���</a> | 
	<a href="joinForm">ȸ������</a> -->
	&nbsp;&nbsp;&nbsp;
</div>

<!-- 
<form action="Tiket.jsp" method="post" align="center">
		<input type="submit" style="width:60pt;height:60pt;background: yellow;font-size:25pt;" value="����">
</form>
-->
<div class="container tiketing">
<div onclick="location.href='reservation/add?key=1';" class="btn btn-secondary">
	<span>����</span>
</div>


<form action="reservation/list" method="post" align="center">
		<input type="submit" " value="����" class="btn btn-secondary">
</form>

<form action="/" method="post" align="center">
		<input type="submit" " value="�󿵽ð�ǥ" class="btn btn-secondary">
</form>
</div>
</div>
