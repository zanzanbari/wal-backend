package backend.wal.reservation.application.port.in;

import backend.wal.reservation.application.port.in.dto.ReservationNotificationRequestDto;

public interface ReservationNotificationUseCase {

    void send(ReservationNotificationRequestDto requestDto);

    void cancel(Long reservationId);
}
