package backend.wal.notification.application.port.in;

public interface NotificationUseCase {

    void sendMessage(Long reservationId, Long userId, String contents);
}
