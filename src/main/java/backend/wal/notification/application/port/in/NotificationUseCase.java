package backend.wal.notification.application.port.in;

public interface NotificationUseCase {

    void sendReservation(Long reservationId, Long userId, String message);
}
