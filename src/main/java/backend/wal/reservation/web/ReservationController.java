package backend.wal.reservation.web;

import backend.wal.reservation.application.port.in.dto.RegisterReservationResponseDto;
import backend.wal.reservation.application.port.in.DeleteReservationHistoryUseCase;
import backend.wal.reservation.application.port.in.ReservationHandlerUseCase;
import backend.wal.reservation.application.port.in.ReservationNotificationUseCase;
import backend.wal.reservation.web.dto.AddReservationRequest;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v2/reservation")
public class ReservationController {

    private final ReservationHandlerUseCase reservationHandlerUseCase;
    private final ReservationNotificationUseCase reservationNotificationUseCase;
    private final DeleteReservationHistoryUseCase deleteReservationHistoryUseCase;

    public ReservationController(final ReservationHandlerUseCase reservationHandlerUseCase,
                                 final ReservationNotificationUseCase reservationNotificationUseCase,
                                 final DeleteReservationHistoryUseCase deleteReservationHistoryUseCase) {
        this.reservationHandlerUseCase = reservationHandlerUseCase;
        this.reservationNotificationUseCase = reservationNotificationUseCase;
        this.deleteReservationHistoryUseCase = deleteReservationHistoryUseCase;
    }

    @Authentication
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AddReservationRequest request, @LoginUser Long userId) {
        RegisterReservationResponseDto responseDto = reservationHandlerUseCase.register(request.toServiceDto(userId));
        reservationNotificationUseCase.send(responseDto.toPublishRequestDto());
        return ResponseEntity.created(URI.create("/v2/reservation/history")).build();
    }

    @Authentication
    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long reservationId, @LoginUser Long userId) {
        reservationNotificationUseCase.cancel(reservationId);
        reservationHandlerUseCase.deleteIfCanceledReservationIsToday(userId);
        return ResponseEntity.noContent().build();
    }

    @Authentication
    @DeleteMapping("/history/{reservationId}/remove")
    public ResponseEntity<Void> remove(@PathVariable Long reservationId) {
        deleteReservationHistoryUseCase.deleteHistory(reservationId);
        return ResponseEntity.noContent().build();
    }
}
