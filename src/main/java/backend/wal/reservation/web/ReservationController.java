package backend.wal.reservation.web;

import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;
import backend.wal.reservation.application.port.dto.RegisterReservationResponseDto;
import backend.wal.reservation.application.service.ReservationNotificationService;
import backend.wal.reservation.web.dto.AddReservationRequest;
import backend.wal.reservation.application.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationNotificationService reservationNotificationService;

    @Authentication
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AddReservationRequest request, @LoginUser Long userId) {
        RegisterReservationResponseDto responseDto = reservationService.register(request.toServiceDto(userId));
        reservationNotificationService.send(responseDto.toPublishRequestDto());
        return ResponseEntity.created(URI.create("/v2/reservation")).build();
    }

    @Authentication
    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long reservationId) {
        reservationNotificationService.cancel(reservationId);
        return ResponseEntity.noContent().build();
    }
}