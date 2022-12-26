<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.lang.Math"%>
<%@page import="vo.Reservation"%>
<%@page import="vo.CountInfo"%>


<link rel="stylesheet" href="/css/vanillawebprojects/style.css" />
<link rel="stylesheet" href="/css/VwyxyBQ/style.css" />

<section id="reservation">
	<table class="container">
		<caption>예매 내역 리스트</caption>
		<thead>
			<tr>
				<th>예약 번호</th>
				<th>좌석 번호</th>
				<th>예약 일시</th>
				<th>취소 일시</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<%
			    List<Reservation> reservations = (List) request.getAttribute("reservations");
				CountInfo countInfo = (CountInfo) request.getAttribute("countInfo");

				if (reservations == null || reservations.size() < 1) {
					out.println("<tr><td colspan=\"5\">예매 내역이 없습니다.</td></tr>");
				} else {
					for (Reservation reservation : reservations) {
						out.println("<tr class=\"item\">");
						out.println("<td>" + reservation.getMovieScreeningId() + "</td>"); // 상영 시퀀스
						out.println("<td>" + reservation.getSeats() + "</td>"); // 좌석번호
						out.println("<td>" + reservation.getReservatedAt() + "</td>"); // 예약 일시
						out.println(
								"<td>" + (reservation.getCancelAt() == null ? "" : reservation.getCancelAt()) + "</td>"); // 취소 일시

						out.println("<td><button class=\"btn info view\" onclick=\"detail(" + reservation.getId()
								+ ")\">자세히 보기</button>"); // 자세히 보기

						// 예약 취소 되지 않은 건 + 미래의 예약 건만 수정/취소가 가능함
						if (reservation.getCancelAt() == null) {
							out.println("<button class=\"btn delete\" onclick=\"cancel(" + reservation.getId()
									+ ")\">예약 취소</button>"); // 예약 취소
						}
						out.println("</td>");
						out.println("</tr>");
					}
				}
			%>
		</tbody>
	</table>

	<%
		String pageNoparam = request.getParameter("pageNo");
		pageNoparam = (pageNoparam == null ? "1" : pageNoparam);
		
		out.println("<div class=\"page\">");
		out.println("</div>");
	%>
</section>
<form action="/" method="post">
			<input type="submit" " value="홈 돌아가기">
			</form>

<style>
.page{
    background-color: #eee;
    text-align: center;
    padding: 5px;
    color: black;
}
.pg_btn{
	padding: 3px;
	font-weight: bold;
}
</style>
	<script src="/js/jquery-3.6.1.min.js"></script>
<script>
	const pageSize = 20;
	var pageNo = <%= pageNoparam %>;
	
	function makePage(){
		var totalCount = <%= countInfo.getTotalCount() %>;
		var totPagePt = Math.ceil(totalCount / pageSize);
		var pageStt = (Math.ceil(pageNo / pageSize) - 1) * pageSize + 1;
		var pageEnd = Math.ceil(pageNo / pageSize) * pageSize;
		pageEnd = pageEnd > totPagePt ? totPagePt : pageEnd;
		var pageTmp = "";

		pageTmp += '<a href="#" class="pg_btn first" ' + (pageStt > 5 ? 'onclick="loadList(1)"' : '') + '></a>' 
				+ '<a href="#" class="pg_btn prev" ' + (pageStt > 5 ? 'onclick="loadList(' + (pageStt - 1) + ')"' : '' ) + '></a>';
		for (var inx = pageStt; inx <= pageEnd; inx++) {
			pageTmp += '<a href="#" class="pg_btn ' + (inx == pageNo ? "active" : "") + '" '
					+ 'onclick="loadList(' + (inx) + ')">' + (inx) + '</a>';
		}
		pageTmp += '<a href="#" class="pg_btn next" '
				+ (totPagePt > pageEnd ? 'onclick="loadList(' + (pageEnd + 1) + ')"' : "") + '></a>'
				+ '<a href="#" class="pg_btn last" '
				+ (totPagePt > pageEnd ? 'onclick="loadList(' + (totPagePt) + ')"' : "") + '></a>';
				
		$('.page').html(pageTmp);
	}
	// 상세 화면 이동
	function detail(id) {
		location.href = '/reservation/detail?id=' + id;
	}
	// 페이징
	function loadList(pageNo) {
		location.href = '/reservation/list?pageSize=' + pageSize + '&pageNo='
				+ pageNo;
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
