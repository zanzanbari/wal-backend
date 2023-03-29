package backend.wal.reservation.application.port;

import backend.wal.reservation.application.port.dto.AddReservationRequestDto;
import backend.wal.reservation.application.port.dto.RegisterReservationResponseDto;

public interface RegisterReservationUseCase {

    RegisterReservationResponseDto register(AddReservationRequestDto requestDto);
}
