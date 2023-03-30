package backend.wal.reservation.application.port.in;

import backend.wal.reservation.web.dto.ReservationCalendarResponse;
import backend.wal.reservation.web.dto.ReservationHistoryResponse;

import java.util.List;

public interface ReservationRetrieveUseCase {

    ReservationHistoryResponse retrieveReservationHistory(Long userId);

    List<ReservationCalendarResponse> retrieveReservationDate(Long userId);
}
