<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<section class="contents" id="step1">
    <link rel="stylesheet" href="/css/VwyxyBQ/style.css" />
	<table>
        <caption>상영 영화 목록</caption>
        <thead>
            <tr>
                <th>영화 선택</th>
                <th>날짜 선택</th>
                <th>예약 하기</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td id="movies">
                <%-- 
                	<a href="#" onclick="showMovieTimes(id)">영화 이름</a><br>
                	--%>
                </td>
                <td id="times">
                <%-- 
                	<a href="#" onclick="showMovieDetail(id, screening_id)">영화 시간</a><br>
                	--%>
                </td>
                <td>
				    <span id="details">-</span>
                    <button id="btnOpenSeat" class="view" onclick="showStep2()">좌석 선택</button>
                </td>
            </tr>
        </tbody>
        <tfoot>
            <tr><td colspan="3" class="tablefoot"></td>
        </tr></tfoot>
    </table>
    
    		<form action="/" method="post">
			<input type="submit" " value="돌아가기">
			</form>
			<div id="map" style="width:100%;height:350px;"></div>
<caption>상영관 위치</caption>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d75219e80911198b462596b376134862"></script>
<script>

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(37.52972651319289,126.96395252493481), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption);

// 마커가 표시될 위치입니다 
var markerPosition  = new kakao.maps.LatLng(37.52972651319289,126.96395252493481); 

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);

var iwContent = '<div style="padding:5px;">용산 cgv', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new kakao.maps.LatLng(37.52972651319289,126.96395252493481); //인포윈도우 표시 위치입니다

// 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({
    position : iwPosition, 
    content : iwContent 
});
  
// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
infowindow.open(map, marker); 
</script>
</section>

<%
// 스타일 참고
// https://github.com/bradtraversy/vanillawebprojects
%>

<section class="contents" id="step2" style="display:none;">
    <link rel="stylesheet" href="/css/vanillawebprojects/style.css" />
    
    <div class="movie-container">
      <label>좌석을 선택해주세요:</label>
    </div>

    <ul class="showcase">
      <li>
        <div class="seat"></div>
        <small>N/A</small>
      </li>
      <li>
        <div class="seat selected"></div>
        <small>Selected</small>
      </li>
      <li>
        <div class="seat occupied"></div>
        <small>Occupied</small>
      </li>
    </ul>

    <div class="container" id="seatView">
      <div class="screen"></div>

      <div class="row">
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat"></div>
      </div>
      <div class="row">
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat"></div>
        <div class="seat occupied"></div>
        <div class="seat occupied"></div>
        <div class="seat occupied"></div>
        <div class="seat"></div>
      </div>
    </div>

    <p class="text">
       좌석 : <span id="count">0</span> <br>
       가격 : <span id="total">0</span>
    </p>
    
    <div style="text-align: center; padding-top: 30px;">
    	<button class="delete" onclick="location.reload()">영화/상영관 선택으로 돌아가기</button>
    	<button class="view" onclick="openKakaoPay()">결제</button>
    </div>

</section>


	<script src="/js/jquery-3.6.1.min.js"></script>
    <script src="/js/vanillawebprojects/script.js"></script>
<script>
var movie_id;
var movie_title;
var movie_screening_id;
var movie_seats;
var movieScreeningInfos;

//영화 목록을 조회합니다.
function showMovies(){
	var moviesTd = document.getElementById("movies");
	moviesTd.innerHTML = '';
	
	// ajax 결과 내용으로 다시 세팅
	$.ajax({
		type : "GET",
		url : "/movie/list.ajax",
		cache : false,
		async : false,
		dataType : "json",
		success : function(res) {
			if (res.result ) {
				res.data = JSON.parse(res.data);
				makeMovieTag(res.data);
			} else {
				alert('오류가 발생하였습니다.');
				console.log(res);
			}
		}
	});
}
// 영화 태그를 생성합니다.
function makeMovieTag(list){
	if(!list){
		alert("상영중인 영화가 없습니다.");
		return;
	}
	var innerTags = '';
	for(var inx = 0; inx < list.length; inx++){
		innerTags = innerTags + '<a href="#" onclick="showMovieTimes('+list[inx].id+', \''+list[inx].title+'\')">'+list[inx].title+'</a><br>';
	}
	var moviesTd = document.getElementById("movies");
	moviesTd.innerHTML = innerTags;
}

// 상영 정보를 조회합니다.
function showMovieTimes(id, title){
	movie_id = id;
	movie_title = title;

	var timesTd = document.getElementById("times");
	timesTd.innerHTML = '';
	
	// ajax 결과 내용으로 다시 세팅
	$.ajax({
		type : "GET",
		url : "/movie/screenings.ajax?movieId=" + id,
		cache : false,
		async : false,
		dataType : "json",
		success : function(res) {
			if (res.result ) {
				res.data = JSON.parse(res.data);
				makeMovieScreeningTag(res.data);
			} else {
				alert('오류가 발생하였습니다.');
				console.log(res);
			}
		}
	});
}
// 상영정보 태그를 생성합니다.
function makeMovieScreeningTag(list){
	if(!list){
		alert("상영중인 영화가 없습니다.");
		return;
	}
	var innerTags = '';
	for(var inx = 0; inx < list.length; inx++){
		innerTags = innerTags + '<a href="#" onclick="showMovieDetail('+list[inx].id+')">'+list[inx].startedAt + '(' + list[inx].theaterNo+' 번 상영관)</a><br>';
	}
	var timesTd = document.getElementById("times");
	timesTd.innerHTML = innerTags;
	
	movieScreeningInfos = list; // 좌석을 위해 정보를 저장합니다.
}

// 상영 정보를 선택합니다.
function showMovieDetail(id){
	movie_screening_id = id;
	
	var movieScreeningInfo = movieScreeningInfos.filter(movieScreeningInfo => movieScreeningInfo.id == id)[0]; // 선택한 상영관 정보

	var detailsSpan = document.getElementById("details");
	detailsSpan.innerHTML = movieScreeningInfo.currentSeatCount + ' / ' + movieScreeningInfo.maxSeatCount; // 남은좌석 / 총 좌석

	// 남은 좌석이 없을 경우 ( == 0) 좌석 예약 버튼 숨기기
	if(movieScreeningInfo.currentSeatCount == 0) {
		var btnOpenSeat = document.getElementById("btnOpenSeat");
		btnOpenSeat.style.display = 'none';
	} else {
		var btnOpenSeat = document.getElementById("btnOpenSeat");
		btnOpenSeat.style.display = '';
	}
	
	// 좌석 세팅 (화면에 그리기)
	const seats = movieScreeningInfo.seatLocation;
	
	var seatViewDiv = document.getElementById("seatView");
	seatViewDiv.innerHTML = '';

	var seatViewTmp = '<div class="screen"></div>';
	var row = '';
	for(var inx = 0; inx < seats.length; inx++){
		var location = seats[inx].location;
		var thisRow = location.charAt(0);
		if(row != thisRow) {
			// 행이 바뀌는 경우 ex) A10 -> B1
			seatViewTmp = seatViewTmp + (inx > 0 ? '</div>' : '') + '<div class="row">'; // 중간 3항 연산자는 첫행이 아닌경우 div를 자동으로 닫아주도록 합니다.
			row = thisRow;
		}
		seatViewTmp = seatViewTmp + '<div class="seat '+( seats[inx].isPossibleReservated ? '' : 'occupied' )+'">'+seats[inx].location+'</div>';

		if(inx+1 >= seats.length) {
			// 마지막 행인 경우
			seatViewTmp = seatViewTmp + '</div>';
		}
	}
	seatViewDiv.innerHTML = seatViewTmp;
	
}

function showStep1(){
	var stepSection2 = document.getElementById("step2");
	stepSection2.style.display = 'none';
	var stepSection1 = document.getElementById("step1");
	stepSection1.style.display = '';
}
function showStep2(){
	var stepSection2 = document.getElementById("step2");
	stepSection2.style.display = '';
	var stepSection1 = document.getElementById("step1");
	stepSection1.style.display = 'none';
	
    seatsInit();
}

// 참고 : https://tjddnjs625.tistory.com/46
function isMobile() {//모바일pc여부확인
	return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
}

// 카카오 페이 결제창을 엽니다.	
function openKakaoPay(){
	var movieTitle = movie_title;
	var movieScreeningId = movie_screening_id;
	var seats = movie_seats;
	
	const quantity = document.getElementById('count').innerText;
	const price = document.getElementById('total').innerText;
	
	var formData = 'movie_title=' + movieTitle + '&movieScreeningId=' + movieScreeningId + '&price=' + price  + '&seats=' + seats  + '&quantity=' + quantity ;
	
	$.ajax({
		type : "POST",
		url : "/reservation/pay-ready.ajax?" + formData,
		success : function(res) {
			res = JSON.parse(res);
			console.log(res);
			if (res.result ) {
				res.data = JSON.parse(res.data);
				res.data = JSON.parse(res.data);
//				res.data.tid;
				if(isMobile()) {
					location.href = res.data.next_redirect_mobile_url;
				} else {
					location.href = res.data.next_redirect_pc_url;
				}
			} else {
				alert('오류가 발생하였습니다.');
				console.log(res);
			}
		}
	});
}

showMovies();
showStep1();
</script>



