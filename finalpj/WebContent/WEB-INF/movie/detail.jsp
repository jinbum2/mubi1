<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.lang.Math"%>
<%@page import="vo.Movie"%>
<%@page import="vo.MovieScreening"%>
<%@page import="vo.Movie"%>


<link rel="stylesheet" href="/css/vanillawebprojects/style.css" />
<link rel="stylesheet" href="/css/VwyxyBQ/style.css" />

<%
    Movie movie = (Movie) request.getAttribute("movie");
	Object userKey = request.getAttribute("userKey");
%>

<section id="movie" class="detail">
	<table class="container">
		<caption>영화 상세</caption>
		<thead>
			<tr>
				<th colspan="4"><%=movie.getTitle()%></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th colspan="4"><img src="<%=movie.getImage()%>"
					style="min-width: 50vw; height: auto;"></th>
			</tr>
			<tr>
				<th>영화명</th>
				<td><%=movie.getTitle()%></td>
				<th>상영시간</th>
				<td>02:00</td>
			</tr>
			<tr>
				<th>평점</th>
				<td><%=(movie.getStarPointAvg() == null ? 0 : movie.getStarPointAvg())%></td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
</section>

<section id="review" class="list">
	<table class="container">
		<caption>리뷰</caption>
		<thead>
			<tr>
				<th>별점</th>
				<th>내용</th>
				<th>작성 시간</th>
				<th>작성자</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody id="reviews">
		<!-- 
			<tr>
				<th>
            		<span class="star">
					  ★★★★★
					  <span style="width: 30%;">★★★★★</span>
					</span>
				</th>
				<td>
					내용
				</td>
				<th>작성시간</th>
				<td>작성자</td>
			</tr>
			-->
		</tbody>
        <tfoot>
            <tr>
            	<td colspan="5" class="tablefoot"><button class="view" onclick="moreReview()">더보기</button></td>
        	</tr>
        	
        	<% 
        	if(userKey == null) {
        	    // 비회원은 댓글 작성 불가
        	} else {
        	    %>
	            <tr>
	            	<td>
	            	<% 
	            	// 참고 https://gurtn.tistory.com/80
	            	%>
	            		<span class="star">
						  ★★★★★
						  <span>★★★★★</span>
						  <input type="range" id="starPoint" oninput="drawStar(this)" value="1" step="1" min="0" max="10">
						</span>
	            	</td>
	            	<td colspan="3">
	            		<textarea id="reviewContents"></textarea>
	            	</td>
	            	<td>
	            		<button class="view" onclick="addReview()">작성</button>
	            	</td>
	        	</tr>
        	    <%
        	}
        	%>
        </tfoot>
	</table>
</section>

<section id="foot" class="list">
	<div style="text-align: center; padding-top: 30px;">
		<button class="view" onclick="list()">뒤로가기</button>
		<button class="view" onclick="toggleReview()">댓글 토글</button>
		<form action="/" method="post">
			<input type="submit" " value="홈 돌아가기">
			</form>
	</div>
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
function drawStar(target) {
    var starPoint = document.querySelector('#starPoint');
    starPoint.parentNode.children[0].style.width = (target.value * 10) + '%';
  }
</script>
<script>
	const pageSize = 20;
	var pageNo = 1;
	var movieId = <%= movie.getId() %>;
	var userId = <%= userKey %>;
	
	// 리뷰 불러오기
	function moreReview(){
		$.ajax({
			type : "POST",
			url : "/review/list.ajax?movieId=" + movieId + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
			cache : false,
			async : false,
			dataType : "json",
			success : function(res) {
				if (res.result) {
					pageNo = pageNo + 1;
					res.data = JSON.parse(res.data);
					for(var inx = 0; inx < res.data.length; inx++){
						makeReviewTag(res.data[inx]);
					}
				} else {
					alert('조회에 실패하였습니다. 잠시후 다시 시도해 주세요.');
					console.log(res);
				}
			}
		});
	}
	function makeReviewTag(review){
		var tmpTag = '<tr>'
			+'		<th>'
			+'   		<span class="star">'
			+'			  ★★★★★'
			+'				  <span style="width: ' + ((review.starPoint / 10) * 100) + '%;">★★★★★</span>'
			+'			</span>'
			+'		</th>'
			+'		<td>'
			+'			' + review.contents
			+'		</td>'
			+'		<th>' + review.createdAt + '</th>'
			+'		<td>' + review.userStrId + '</td>'
			+'		<td>' + (review.userId == userId ? '<button class="delete" onclick="removeReview('+review.id+')">X</button>' : '-') + '</td>'
			+'	</tr>';
		$('#reviews').html($('#reviews').html() + tmpTag);
	}
	// 리뷰 작성
	function addReview(){
        var contents = $('#reviewContents').val();
        var starPoint = $('#starPoint').val();
        
        var params = 'movieId='+movieId+'&contents='+contents+'&starPoint='+starPoint;
        
		$.ajax({
			type : "POST",
			url : "/review/add.ajax?" + params,
			cache : false,
			async : false,
			dataType : "json",
			success : function(res) {
				if (res.result) {
					alert('등록 되었습니다.');
					location.reload();
				} else {
					alert('리뷰가 등록되지 않았습니다. 잠시후 다시 시도해 주세요.');
					console.log(res);
				}
			}
		});
	}
	// 리뷰 삭제
	function removeReview(id){
		if(! confirm('정말 삭제하시겠습니까?')) {
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "/review/remove.ajax?id=" + id,
			cache : false,
			async : false,
			dataType : "json",
			success : function(res) {
				if (res.result) {
					alert('삭제되었습니다.');
					location.reload();
				} else {
					alert('리뷰가 삭제되지 않았습니다. 잠시후 다시 시도해 주세요.');
					console.log(res);
				}
			}
		});
	}
	// 목록 화면 이동
	function list() {
		history.back();
	}
	// 리뷰 토글
	function toggleReview() {
		$('#review').toggle();
	}
	moreReview();
</script>