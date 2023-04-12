package backend.wal.reservation.application.port.in;

import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;
import backend.wal.reservation.application.port.in.dto.RegisterReservationResponseDto;

public interface ReservationHandlerUseCase {

    RegisterReservationResponseDto register(AddReservationRequestDto requestDto);

    void deleteIfCanceledReservationIsToday(Long userId);
}
