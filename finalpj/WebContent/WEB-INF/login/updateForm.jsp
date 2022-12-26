<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 
<%//로그인여부체크 (로그인 실패시->홈으로)
String id = (String) session.getAttribute("id");
if(id == null){
	response.sendRedirect("/");
} 


%>
<fieldset>
<legend>회원정보수정</legend>
	<form name="update" action="updateprocess" method="post">
<tr><td>아이디 : </td><td><input type="text" name="id"></td></tr>
 <tr><td>비밀번호 : </td><td><input type="password" name="pw"></td></tr>
 <tr><td>이름 : </td><td><input type="text" name="name"></td></tr>
 <tr><td>생년월일 : </td><td><input type="text" name="birth" size=15></td></tr>
 <tr><td>이메일 : </td><td><input type="email" name="email" size=30></td></tr>
 <tr><td>전화번호 : </td><td><input type="tel" name="tel" size=30></td></tr>
 <tr><td>주소 : </td><td><input type="text" name="address" size=15></td></tr>
		<input type="submit" value="회원정보수정하기">
	</form>
</fieldset>

<button onclick="location.href='/'">뒤로가기</button>
-->