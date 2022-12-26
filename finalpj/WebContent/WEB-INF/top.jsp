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
    /*태두리채우자 */
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
        id = (String) session.getAttribute("id");//로그인을 성공적으로 수행하면 메인창 입장.

	%>
	<%-- <b><%=id%></b>님 환영 합니다. --%>
	<div class="container loginBox">
	<!-- <a href="deleteForm">회원탈퇴</a> -->
	<!-- <a href="updateForm">회원정보수정</a>-->
	<a href="logout">로그아웃</a>
	</div>
	<%
		} else {
	%>
	<div class="container loginBox">
	<form action="loginForm"method="post" >
	<input type="submit" value="로그인" class="btn btn-secondary">
	</form> 
	<form action="joinForm"method="post" >
	<input type="submit" value="회원가입" class="btn btn-warning">
	</form>
	</div>
	<%
		}
	%>
	
	
	
	<!-- <a href="loginForm">로그인</a> | 
	<a href="joinForm">회원가입</a> -->
	&nbsp;&nbsp;&nbsp;
</div>

<!-- 
<form action="Tiket.jsp" method="post" align="center">
		<input type="submit" style="width:60pt;height:60pt;background: yellow;font-size:25pt;" value="예매">
</form>
-->
<div class="container tiketing">
<div onclick="location.href='reservation/add?key=1';" class="btn btn-secondary">
	<span>예매</span>
</div>


<form action="reservation/list" method="post" align="center">
		<input type="submit" " value="예약" class="btn btn-secondary">
</form>

<form action="/" method="post" align="center">
		<input type="submit" " value="상영시간표" class="btn btn-secondary">
</form>
</div>
</div>
