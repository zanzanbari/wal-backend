package backend.wal.reservation.application.port.in;

public interface DeleteReservationHistoryUseCase {

    void deleteHistory(Long reservationId);
}
