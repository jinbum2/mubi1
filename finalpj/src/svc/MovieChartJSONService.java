package svc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import vo.MovieChart;

public class MovieChartJSONService {//무비차트서비스

	public ArrayList<MovieChart> parsingKobisJSON() throws Exception {
		ArrayList<MovieChart> result = new ArrayList<MovieChart>();
		/* JSON파싱 */
		JSONParser jsonparser = new JSONParser();
		JSONObject jsonobject = (JSONObject) jsonparser.parse(kobisMovieChartUrl());
		JSONObject json = (JSONObject) jsonobject.get("boxOfficeResult");
		JSONArray array = (JSONArray) json.get("dailyBoxOfficeList");
		
		// 여기 dailyBoxOfficeList array 가 사이즈가 0이다 이게문제인거같음 
		for (int i = 0; i < array.size(); i++) {
			String[] pubDate = null;
			JSONObject entity = (JSONObject) array.get(i);

			MovieChart movieChart = new MovieChart();
			movieChart.setRnum((String) entity.get("rnum")); // 순위
			movieChart.setRank((String) entity.get("rank"));
			movieChart.setMovieNm((String) entity.get("movieNm"));// movie이름
			movieChart.setOpenDt((String) entity.get("openDt")); // 개봉일
			movieChart.setSalesAcc((String) entity.get("salesAcc"));// 누적매출액
			movieChart.setAudiAcc((String) entity.get("audiAcc"));// 누적관객수
			pubDate = movieChart.getOpenDt().split("-");
			
			System.out.println(movieChart.getMovieNm()); // 어떤 영화는 박스오피스에만 있고,네이버 api에 없는게 문제같다
			try {
				movieChart.setImage(parsingNaverJSON(movieChart.getMovieNm(), pubDate[0])); // imageURL
			}catch(Exception e) {
				e.getStackTrace();
				continue;
			} // 이런식으로 예외처리(시험에서 나온 수신측 예외처리)를 하여 두 API 간 교집합을 뽑을 수 있다.
			

			result.add(movieChart);
			
		}
		return result;
	}

	private static String kobisMovieChartUrl() throws Exception {
		// 포스트맨으로 확인결과 영화 진흥청 목록과 네이버 목록 간의 싱크가 맞지않는듯하다 
		
		String key = "abe2fe938d48f92b22498ba6049e5ecb";
		String result = null;

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 어제의 박스오피스를 가져온다
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String day = simpleDateFormat.format(calendar.getTime());

		String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json"
				+ "?key=" + key + "&targetDt=" + day;
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else { // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		result = response.toString();
		br.close();

		return result;
	}
			//영화 포스터
	private static String naverImageUrl(String movieNm, String pubDate) throws Exception {
		String clientId = "lHVmULPa1i1UltPgijR0";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "7T4ycQI7YW";// 애플리케이션 클라이언트 시크릿값";
		String result = null;

		String text = URLEncoder.encode(movieNm, "UTF-8");
		String apiURL = "https://openapi.naver.com/v1/search/movie.json?" + "query=" + text + "&display=1"
				+ "&yearfrom=" + pubDate + "&yearto=" + pubDate;
		System.out.println(apiURL);
		
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-Naver-Client-Id", clientId);
		con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else { // 에러 발생 << 에러가 발생해도 그냥 같은 처리를해서 html을 가져오는걸 막는다
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		result = response.toString();
		br.close();

		return result;
	}

	private String parsingNaverJSON(String movieNm, String pubDate) throws Exception {
		String imageURL = null;

		JSONParser jsonparser = new JSONParser();
		String response = naverImageUrl(movieNm, pubDate); // << status code 가 200 계열이 아니면 반드시 
		// response text 가 존재하지는 않을 수 있다 
		// 존재는 하는데, 네이버에 없는 값이 오니까 json이 아니고 html을 가져온거같다 그래서  난 에러
		
		System.out.println(response);
		
		
		JSONObject jsonobject = (JSONObject) jsonparser.parse(response);

		JSONArray array = (JSONArray) jsonobject.get("items");
		for (int i = 0; i < array.size(); i++) {
			JSONObject entity = (JSONObject) array.get(i);
			imageURL = (String) entity.get("image");
		}
		return imageURL;
	}
}