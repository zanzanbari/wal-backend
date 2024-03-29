package backend.wal.reservation.web;

import backend.wal.reservation.web.dto.ReservationCalenderResponse;
import backend.wal.reservation.web.dto.ReservationHistoryResponse;
import backend.wal.reservation.application.service.ReservationRetrieveService;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/reservation")
public class ReservationRetrieveController {

    private final ReservationRetrieveService reservationRetrieveService;

    public ReservationRetrieveController(final ReservationRetrieveService reservationRetrieveService) {
        this.reservationRetrieveService = reservationRetrieveService;
    }

    @Authentication
    @GetMapping("/history")
    public ResponseEntity<ReservationHistoryResponse> retrieveHistory(@LoginUser Long userId) {
        return ResponseEntity.ok(reservationRetrieveService.retrieveReservationHistory(userId));
    }

    @Authentication
    @GetMapping("/calender")
    public ResponseEntity<ReservationCalenderResponse> retrieveCalendar(@LoginUser Long userId) {
        return ResponseEntity.ok(ReservationCalenderResponse.create(
                reservationRetrieveService.retrieveReservationDate(userId)));
    }
}
