package backend.wal.reservation.web;

import backend.wal.reservation.application.port.in.dto.RegisterReservationResponseDto;
import backend.wal.reservation.application.port.in.DeleteReservationHistoryUseCase;
import backend.wal.reservation.application.port.in.RegisterReservationUseCase;
import backend.wal.reservation.application.port.in.ReservationNotificationUseCase;
import backend.wal.reservation.web.dto.AddReservationRequest;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/reservation")
public class ReservationController {

    private final RegisterReservationUseCase registerReservationUseCase;
    private final ReservationNotificationUseCase reservationNotificationUseCase;
    private final DeleteReservationHistoryUseCase deleteReservationHistoryUseCase;

    @Authentication
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AddReservationRequest request, @LoginUser Long userId) {
        RegisterReservationResponseDto responseDto = registerReservationUseCase.register(request.toServiceDto(userId));
        reservationNotificationUseCase.send(responseDto.toPublishRequestDto());
        return ResponseEntity.created(URI.create("/v2/reservation")).build();
    }

    @Authentication
    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long reservationId) {
        reservationNotificationUseCase.cancel(reservationId);
        return ResponseEntity.noContent().build();
    }

    @Authentication
    @PostMapping("/history/{reservationId}/remove")
    public ResponseEntity<Void> remove(@PathVariable Long reservationId) {
        deleteReservationHistoryUseCase.deleteHistory(reservationId);
        return ResponseEntity.noContent().build();
    }
}
