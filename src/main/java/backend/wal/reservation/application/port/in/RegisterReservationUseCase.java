package backend.wal.reservation.application.port.in;

import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;
import backend.wal.reservation.application.port.in.dto.RegisterReservationResponseDto;

public interface RegisterReservationUseCase {

    RegisterReservationResponseDto register(AddReservationRequestDto requestDto);
}
