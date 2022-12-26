<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ page import="vo.MovieChart"%>
<%@ page import="java.util.*"%>

<%
	request.setCharacterEncoding("EUC-KR");
	ArrayList<MovieChart> movieCharts = (ArrayList<MovieChart>) request.getAttribute("movieCharts");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/movieChart.css">
<meta charset="EUC-KR" />
<title>파싱</title>
</head>
<body>
	<%
		if (movieCharts != null) {
	%>
	<div align="center">
		<%
			for (int i = 0; i < movieCharts.size(); i++) {//사진
		%>
			<span><img src="<%=movieCharts.get(i).getImage()%>"style="float : left;width:90pt;height:100pt;"/></span>
		<%
			} // end for
		%>
	</div>
	<div class="caption" align="center">오늘의 영화 순위</div>
	<div id="table">
		<div class="header-row row" >
			<span class="cell">순위</span>
			<span class="cell">이름</span>
			<span class="cell">개봉일</span>
			<span class="cell">누적관객수</span>
		
		</div>
		<%
			for (int i = 0; i < movieCharts.size(); i++) {
		%>
		<div class="row">
			<span class="cell" data-label="순위"><%=movieCharts.get(i).getRank()%></span>
			<span class="cell" data-label="이름"><%=movieCharts.get(i).getMovieNm()%></span>
			<span class="cell" data-label="개봉일"><%=movieCharts.get(i).getOpenDt()%></span>
			<span class="cell" data-label="누적관객수"><%=movieCharts.get(i).getAudiAcc()%></span>
			
		</div>
		<%
			} // end for
		%>
	</div>
	<%
		} // end if
	%>
</body>
</html>
