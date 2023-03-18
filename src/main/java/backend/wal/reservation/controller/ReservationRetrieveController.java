package backend.wal.reservation.controller;

import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.LoginUser;
import backend.wal.reservation.controller.dto.ReservationCalendarResponse;
import backend.wal.reservation.controller.dto.ReservationHistoryResponse;
import backend.wal.reservation.app.service.ReservationRetrieveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationRetrieveController {

    private final ReservationRetrieveService reservationRetrieveService;

    @Authentication
    @GetMapping("/v2/reservation")
    public ResponseEntity<ReservationHistoryResponse> retrieveHistory(@LoginUser Long userId) {
        return ResponseEntity.ok(reservationRetrieveService.retrieveReservationHistory(userId));
    }

    @Authentication
    @GetMapping("/v2/reservation/calender")
    public ResponseEntity<List<ReservationCalendarResponse>> retrieveCalendar(@LoginUser Long userId) {
        return ResponseEntity.ok(reservationRetrieveService.retrieveReservationDate(userId));
    }
}
