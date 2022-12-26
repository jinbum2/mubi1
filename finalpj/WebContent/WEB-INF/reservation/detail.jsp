<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.lang.Math"%>
<%@page import="vo.Reservation"%>
<%@page import="vo.MovieScreening"%>
<%@page import="vo.Movie"%>


<link rel="stylesheet" href="/css/vanillawebprojects/style.css" />
<link rel="stylesheet" href="/css/VwyxyBQ/style.css" />

<%
    Reservation reservation = (Reservation) request.getAttribute("reservation");
			MovieScreening movieScreening = (MovieScreening) request.getAttribute("movieScreening");
			Movie movie = (Movie) request.getAttribute("movie");

			String status = "예약";
			if (reservation.getDeleted() == 1) {
				status = "삭제";
			} else if (reservation.getCancelAt() != null) {
				status = "취소";
			} else if (reservation.getPaid() == 1) {
				status = "결제완료";
			}
			
			String startPoint = movie.getStarPointAvg() == null ? "0" : movie.getStarPointAvg();
			float star = Float.parseFloat(startPoint);
%>

<section id="reservation">
	<table class="container">
		<caption>예매 내역 상세</caption>
		<thead>
			<tr>
				<th colspan="6"><%=movie.getTitle()%></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th colspan="6"><img src="<%=movie.getImage()%>" style="min-width: 50vw;height: auto;"></th>
			</tr>
			<tr>
				<th>[상태] 영화명</th>
				<td>[<%=status%>] <%=movie.getTitle()%></td>
				<th>상영관 - 예약 좌석</th>
				<td><%=movieScreening.getTheaterNo()%>관 - <%=reservation.getSeats()%></td>
				<th>시작시간 (상영시간)</th>
				<td><%=movieScreening.getStartedAt()%> ~ <%=movieScreening.getEndedAt()%>
					(02:00)</td>
			</tr>
			<tr>
				<th>평점</th>
				<td>
		   			<span class="star">
					  ★★★★★
						  <span style="width: <%= ((star / 10) * 100) %>%;">★★★★★</span>
					</span>
				
				</td>
				<th>예약 일시</th>
				<td><%=reservation.getReservatedAt()%></td>
				<th>취소 일시</th>
				<td><%=(reservation.getCancelAt() == null ? "-" : reservation.getCancelAt())%></td>
			</tr>
			<tr>
				<th>가격</th>
				<td><%=reservation.getPrice()%></td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>목록</th>
				<td><button class="view" onclick="list()">목록</button></td>
				<th>예약 수정</th>
				<td><button class="delete"
						onclick="cancel(<%=reservation.getId()%>)">취소</button></td>
				<th>평점 쓰기</th>
				<td><button class="view"
						onclick="location.href='/movie/detail?id=<%=movie.getId()%>';">평점쓰기</button></td>
			</tr>
		</tbody>
	</table>
</section>

<style>
  .star {
    position: relative;
    font-size: 2rem;
    color: #ddd;
  }
  
  .star input {
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0;
    opacity: 0;
    cursor: pointer;
  }
  
  .star span {
    width: 0;
    position: absolute; 
    left: 0;
    color: red;
    overflow: hidden;
    pointer-events: none;
  }
</style>
<script src="/js/jquery-3.6.1.min.js"></script>
<script>	
	// 목록 화면 이동
	function list() {
		history.back();
	}
	// 예약 취소
	function cancel(id) {
		if (!confirm("예약을 취소하시겠습니까?")) {
			return;
		}

		$.ajax({
			type : "POST",
			url : "/reservation/cancel.ajax?id=" + id,
			cache : false,
			async : false,
			dataType : "json",
			success : function(res) {
				if (res.result) {
					alert('취소되었습니다.');
					location.reload();
				} else {
					alert('예약이 취소되지 않았습니다. 잠시후 다시 시도해 주세요.');
					console.log(res);
				}
			}
		});
	}
	// 예약 수정
	function modify(id) {
		location.href = '/reservation/add?mode=modify&id=' + id;
	}
	makePage();
</script>