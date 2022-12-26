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
  <script type="text/javascript">
  	$(function(){
  		$(".carousel-indicators li:first-child").addClass("active");
  		$(".carousel-inner div:first-child").addClass("active");
  	});
  </script>
  
  <style type="text/css">
  	.movieRan{
  		padding:10px;
  	}
  	  .carousel-inner img {
    width: 100%;
  }
  .container{
  	padding-right: 0px;
  	padding-left: 0px;
  }
  </style>
 
<meta charset="EUC-KR" />
<title>�Ľ�</title>
</head>
<body>
<div class="container">
	<%
		if (movieCharts != null) { 
	%>
	<div align="center">
		
			
	</div>
	
	
	
<div id="demo" class="carousel slide" data-ride="carousel">

  <!-- Indicators -->
  <ul class="carousel-indicators">
  
		
	<%
			for (int i = 0; i < movieCharts.size(); i++) {//����
	%>	
		<li data-target="#demo" data-slide-to="<%= i %>" class=""></li>		
	<% 		
	}
	%>
   
  </ul>

			<!-- The slideshow �̹��� -->
			<div class="carousel-inner">
				<%
					for (int i = 0; i < movieCharts.size(); i++) {//����
				%>

				<div class="carousel-item">
					<img src="<%=movieCharts.get(i).getImage()%>" alt="Los Angeles"
						width="1100" height="500">
				</div>

				<%
					} // end for
				%>

			</div>



			<!-- Left and right controls -->
  <a class="carousel-control-prev" href="#demo" data-slide="prev">
    <span class="carousel-control-prev-icon"></span>
  </a>
  <a class="carousel-control-next" href="#demo" data-slide="next">
    <span class="carousel-control-next-icon"></span>
  </a>
</div>
	
	
	
	

	<!-- <div class="container bg-light movieRan" align="center">������ ��ȭ ����</div> -->
	<div id="table" class="container">
		<div class="header-row row" >
			<span class="cell">����</span>
			<span class="cell">�̸�</span>
			<span class="cell">������</span>

		
		</div>
		<%
			for (int i = 0; i < movieCharts.size(); i++) {
		%>
		<div class="row container chk">
			<span class="cell" data-label="����"><span class="chkCell"><%=movieCharts.get(i).getRank()%></span></span>
			<span class="cell" data-label="�̸�"><%=movieCharts.get(i).getMovieNm()%></span>
			<span class="cell" data-label="������"><%=movieCharts.get(i).getOpenDt()%></span>
			
		</div>
		<%
			} // end for
		%>
	</div>
	<%
		} // end if
	%>
	</div> 
	
	<script type="text/javascript">
		$(function(){
			$("#table .chk:nth-child(4) .chkCell, #table .chk:nth-child(2) .chkCell, #table .chk:nth-child(3) .chkCell").css("background","skyblue");
		})
	</script>
</body>
</html>
