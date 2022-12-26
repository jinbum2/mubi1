package action;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.util.StringUtils;

import svc.MovieService;
import svc.ReservationService;
import utils.ConvertUtil;
import utils.ResponseUtil;
import vo.ActionForward;
import vo.CountInfo;
import vo.Movie;
import vo.MovieScreening;
import vo.Reservation;

public class ReservationAction {

    private ActionForward forward;
    private ReservationService service;

    private final String FOLDER_PATH = "reservation/";

    private final String WEB_URL = "http://localhost:8080/";

    public void initAction() {
        forward = new ActionForward();
        service = new ReservationService();
    }

    private Reservation generateReservationByRequest(HttpServletRequest request) {//예약액션

        String cancelAt = request.getParameter("cancelAt");
        int deleted = ConvertUtil.convertString2Int(request.getParameter("deleted"));
        int id = ConvertUtil.convertString2Int(request.getParameter("id"));
        int movieScreeningId = ConvertUtil.convertString2Int(request.getParameter("movieScreeningId"));
        int paid = ConvertUtil.convertString2Int(request.getParameter("paid"));
        int price = ConvertUtil.convertString2Int(request.getParameter("price"));
        String reservatedAt = request.getParameter("reservatedAt");
        String seats = request.getParameter("seats");

        HttpSession session = request.getSession();
        int userId = Integer.parseInt(session.getAttribute("userKey").toString());

        Reservation reservation = new Reservation();
        reservation.setCancelAt(cancelAt);
        reservation.setDeleted(deleted);
        reservation.setId(id);
        reservation.setMovieScreeningId(movieScreeningId);
        reservation.setPaid(paid);
        reservation.setPrice(price);
        reservation.setReservatedAt(reservatedAt);
        reservation.setSeats(seats);
        reservation.setUserId(userId);

        return reservation;
    }

    // 예약 등록 json (자리 선점)
    public Action addReservationJson() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                service.addReservation(generateReservationByRequest(request));

                PrintWriter writer = response.getWriter();
                writer.write(ResponseUtil.generateJSONResponse(true, null));
                writer.close();
                return null;
            }
        };
    }

    // 예약 페이지로 이동
    public Action addReservationPage() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                
                if (isEmptyUserKey(request)) {
                    return forward;
                }
                
                forward.setPath(FOLDER_PATH + "form.jsp");
                return forward;
            }
        };
    }

    // 예약 수정 json 결과
    public Action modifyReservationJson() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                
                if (isEmptyUserKey(request)) {
                    return forward;
                }
                
                service.modifyReservation(generateReservationByRequest(request));

                PrintWriter writer = response.getWriter();
                writer.write(ResponseUtil.generateJSONResponse(true, null));
                writer.close();
                return null;
            }
        };
    }

    // 예약 취소 json 결과
    public Action cancelReservationJson() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                service.cancelReservation(generateReservationByRequest(request));

                PrintWriter writer = response.getWriter();
                writer.write(ResponseUtil.generateJSONResponse(true, null));
                writer.close();
                return null;
            }
        };
    }

    // 예약 상세 페이지로 이동
    public Action getReservationPage() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                
                if (isEmptyUserKey(request)) {
                    return forward;
                }
                
                int id = ConvertUtil.convertString2Int(request.getParameter("id"));

                // 예약 마스터 데이터
                Reservation reservation = service.getReservation(id);
                request.setAttribute("reservation", reservation);

                // 예약된 상영관 데이터
                MovieService movieService = new MovieService();
                MovieScreening movieScreening = movieService.getMovieScreeningById(reservation.getMovieScreeningId());
                request.setAttribute("movieScreening", movieScreening);

                // 예약된 영화 데이터
                Movie movie = movieService.getMovieById(movieScreening.getMovieId());
                request.setAttribute("movie", movie);

                forward.setPath(FOLDER_PATH + "detail.jsp");
                return forward;
            }
        };
    }
    
    private boolean isEmptyUserKey(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        if(session.getAttribute("userKey") == null) {
            request.setAttribute("error", "로그인을 해주세요.");
            forward.setPath("/error/401");
            forward.setRedirect(true);
            return true;
        }
        String key = session.getAttribute("userKey").toString();

        if (StringUtils.isEmpty(key)) {
            // 로그인 세션이 없을 경우 에러
            request.setAttribute("error", "로그인을 해주세요.");
            forward.setPath("/error/401");
            forward.setRedirect(true);
            return true;
        }
        return false;
    }

    // 예약 목록 페이지로 이동
    public Action getReservationsPage() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();
                
                if (isEmptyUserKey(request)) {
                    return forward;
                }

                HttpSession session = request.getSession();
                String key = session.getAttribute("userKey").toString();
                int userId = Integer.parseInt(key);
                int pageNo = ConvertUtil.convertString2Int(request.getParameter("pageNo"));
                int pageSize = ConvertUtil.convertString2Int(request.getParameter("pageSize"));

                pageNo = (pageNo < 1 ? 1 : pageNo);
                pageSize = (pageSize < 5 ? 20 : pageSize);

                List<Reservation> reservations = service.getReservationByUserId(userId, pageNo, pageSize);
                CountInfo countInfo = service.countReservationByUserId(userId);
                request.setAttribute("reservations", reservations);
                request.setAttribute("countInfo", countInfo);
                forward.setPath(FOLDER_PATH + "list.jsp");
                return forward;
            }
        };
    }

    // 기 예약건 확인 json 결과
    public Action getReservationByMovieScreeningIdJson() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                int movieScreeningId = ConvertUtil.convertString2Int(request.getParameter("movieScreeningId"));

                List<Reservation> reservations = service.getReservationByMovieScreeningId(movieScreeningId);

                PrintWriter writer = response.getWriter();
                JSONArray json = new JSONArray();
                json.addAll(reservations);
                writer.write(ResponseUtil.generateJSONResponse(true, json));
                writer.close();
                return null;
            }
        };
    }

    // 예약 가능한 자리인지 확인 json 결과
    public Action isPossibleReservationJson() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                int movieScreeningId = ConvertUtil.convertString2Int(request.getParameter("movieScreeningId"));
                String seatLocation = request.getParameter("seatLocation");

                boolean isPosible = service.isPossibleReservation(movieScreeningId, seatLocation);

                PrintWriter writer = response.getWriter();
                JSONObject json = new JSONObject();
                json.put("isPosible", isPosible);
                writer.write(ResponseUtil.generateJSONResponse(true, json));
                writer.close();
                return null;
            }
        };
    }

    // 결제 준비 ajax
    // https://developers.kakao.com/docs/latest/ko/kakaopay/single-payment
    public Action kakaoPayReadyAjax() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

                initAction();

                // 결제 프로세스전 예약 (선점)
                // NOTICE: 실제 결제가 외부계 API 및 사용자 취소가 발생할 수 있으므로 결제 중 좌석을 선점하여 문제를 제거한다.
                Reservation reservation = generateReservationByRequest(request);

                String movieTitle = request.getParameter("movie_title");
                String quantity = request.getParameter("quantity");

                boolean isPosible = service.isPossibleReservation(reservation.getMovieScreeningId(),
                    reservation.getSeats());
                if (!isPosible) {
                    // 좌석이 이미 팔림
                    PrintWriter writer = response.getWriter();
                    JSONObject json = new JSONObject();
                    writer.write(ResponseUtil.generateJSONResponse(false, json));
                    writer.close();
                    return null;
                }
                reservation = service.addReservation(reservation);
                HttpSession session = request.getSession();
                session.setAttribute("user-reservation-id", reservation.getId());

                HttpsURLConnection urlConnection;
                StringBuilder stb = new StringBuilder();
                try {
                    URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
                    urlConnection = (HttpsURLConnection)url.openConnection();

                    Map<String, String> headerMap = new HashMap<String, String>();
                    headerMap.put("Authorization", "KakaoAK b1649bcca7590fb2567fb188799efd90");//키바꿔야된다!
                    headerMap.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

                    for (String key : headerMap.keySet()) {
                        urlConnection.setRequestProperty(key, headerMap.get(key));
                    }

                    Map<String, Object> bodyMap = new HashMap<String, Object>();
                    bodyMap.put("cid", "TC0ONETIME"); // test cid
                    String tt = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    bodyMap.put("partner_order_id", "BF-T-"+ tt +"-" + reservation.getId() + "");
                    // 요기 partner_order_id 가 주문번호라 고유식별자여야 해요. 이게 겹치면 방금 오류가 나와요
                    bodyMap.put("partner_user_id", reservation.getUserId() + "");
                    bodyMap.put("item_name", movieTitle);
                    bodyMap.put("item_code", reservation.getMovieScreeningId() + "");
                    bodyMap.put("quantity", Integer.parseInt(quantity));
                    bodyMap.put("total_amount", reservation.getPrice());
                    bodyMap.put("tax_free_amount", 0);
                    bodyMap.put("vat_amount", 0);
                    bodyMap.put("approval_url", WEB_URL + "reservation/pay-complete");
                    bodyMap.put("cancel_url", WEB_URL + "reservation/pay-cancel");
                    bodyMap.put("fail_url", WEB_URL + "reservation/pay-cancel");

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, Object> params : bodyMap.entrySet()) {
                        if (postData.length() != 0)
                            postData.append("&");
                        postData.append(URLEncoder.encode(params.getKey(), "UTF-8"));
                        postData.append("=");
                        postData.append(URLEncoder.encode(String.valueOf(params.getValue()), "UTF-8"));
                    }

                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setReadTimeout(3000);

                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    System.out.println("postData : " + postData.toString());
                    wr.writeBytes(postData.toString());
                    wr.flush();
                    wr.close();

                    //                    urlConnection.connect();

                    int code = urlConnection.getResponseCode();
                    System.out.println("code : " + code);
                    if (code >= 400) {
                        System.out.println("msg : " + urlConnection.getResponseMessage());
                    }

                    BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), Charset.forName("UTF-8")));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stb.append(line);
                    }
                    reader.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PrintWriter writer = response.getWriter();
                writer.write(ResponseUtil.generateJSONResponse(true, stb.toString()));
                writer.close();
                return null;
            }
        };
    }

    // 예약 완료 (kakao 결제 이후)
    public Action completeReservationPage() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                // 본래 승인 로직이 있어야 함.
                HttpSession session = request.getSession();
                String key = session.getAttribute("user-reservation-id").toString();
                
                if (isEmptyUserKey(request)) {
                    return forward;
                }

                int id = ConvertUtil.convertString2Int(key);
                Reservation reservation = service.getReservation(id);
                service.completeReservation(reservation);

                forward.setPath("list");
                forward.setRedirect(true);
                return forward;
            }
        };
    }

    // 결제 삭제 (kakao 결제 이후)
    public Action completeRemoveReservationPage() {
        return new Action() {
            public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                initAction();

                HttpSession session = request.getSession();
                String key = session.getAttribute("user-reservation-id").toString();
                
                if (isEmptyUserKey(request)) {
                    return forward;
                }
                
                int id = ConvertUtil.convertString2Int(key);
                Reservation reservation = service.getReservation(id);
                // 예약을 끝까지 이행하지 않았으므로, 예약 이력에도 노출되면 안되나 관리자는 이력성으로 확인할 수 있도록 soft delete 한다.
                service.removeReservation(reservation);

                forward.setPath("add");
                forward.setRedirect(true);
                return forward;
            }
        };
    }
}
