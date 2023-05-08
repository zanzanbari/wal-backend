package backend.wal.reservation.application.port.in;

public interface UpdateReservationUseCase {

    void updateSendStatusToDone(Long reservationId);
}
