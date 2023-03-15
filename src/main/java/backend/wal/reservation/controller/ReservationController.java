package backend.wal.reservation.controller;

import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.LoginUser;
import backend.wal.rabbitmq.producer.ReservationProducer;
import backend.wal.reservation.app.dto.RegisterReservationResponseDto;
import backend.wal.reservation.controller.dto.AddReservationRequest;
import backend.wal.reservation.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationProducer reservationProducer;

    @Authentication
    @PostMapping("/v2/reservation")
    public ResponseEntity<Void> register(@Valid @RequestBody AddReservationRequest request, @LoginUser Long userId) {
        RegisterReservationResponseDto responseDto = reservationService.register(request.toServiceDto(userId));
        reservationProducer.publishToReservationQueue(responseDto.toPublishRequestDto());
        return ResponseEntity.created(URI.create("/reservation")).build();
    }
}
