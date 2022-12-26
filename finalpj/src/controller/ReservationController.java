package controller;

import javax.servlet.annotation.WebServlet;

import action.Action;
import action.ReservationAction;

@WebServlet({//예약컨트롤러
    "/reservation", "/reservation/list",
    "/reservation/add",
    "/reservation/detail",
    "/reservation/list.ajax",
    "/reservation/cancel.ajax",
    "/reservation/add.ajax",
    "/reservation/modify.ajax",
    "/reservation/complete.ajax",
    "/reservation/posible.ajax",
    "/reservation/pay-ready.ajax",
    "/reservation/pay-cancel",
    "/reservation/pay-complete"
})
public class ReservationController extends AbsFrontHttpServlet {

    private final String PRE_WEB_PATH = "/reservation";

    @Override
    protected Action doCommand(String command) throws Exception {
        ReservationAction reservationAction = new ReservationAction();

        if (command.equals(PRE_WEB_PATH + "/list.ajax")) {//예약 내역 목록
            return reservationAction.getReservationByMovieScreeningIdJson();
        } else if (command.equals(PRE_WEB_PATH + "/cancel.ajax")) {//예약취소json
            return reservationAction.cancelReservationJson();
        } else if (command.equals(PRE_WEB_PATH + "/add.ajax")) {//예약등록json
            return reservationAction.addReservationJson();
        } else if (command.equals(PRE_WEB_PATH + "/add")) {//예약 화면
            return reservationAction.addReservationPage();
        } else if (command.equals(PRE_WEB_PATH + "/detail")) {//예약내역 상세보기
            return reservationAction.getReservationPage();
        } else if (command.equals(PRE_WEB_PATH + "/list") || command.equals(PRE_WEB_PATH + "")) {//예약내역목록
            return reservationAction.getReservationsPage();
        } else if (command.equals(PRE_WEB_PATH + "/posible.ajax")) {//예약가능한좌석인지json확인
            return reservationAction.isPossibleReservationJson();
        } else if (command.equals(PRE_WEB_PATH + "/modify.ajax")) {//예약 수정 json
            return reservationAction.modifyReservationJson();
        } else if (command.equals(PRE_WEB_PATH + "/pay-ready.ajax")) {//카카오페이 결제준비json
            return reservationAction.kakaoPayReadyAjax();
        } else if (command.equals(PRE_WEB_PATH + "/pay-cancel")) {//카카오페이 결제취소 리디렉션
            return reservationAction.completeRemoveReservationPage();
        } else if (command.equals(PRE_WEB_PATH + "/pay-complete")) {//카카오페이 결제스인 리디렉션
            return reservationAction.completeReservationPage();
        }
        return makeErrorAction();
    }
}
