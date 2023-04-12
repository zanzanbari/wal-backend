package backend.wal.notification.application.port.in;

public interface NotificationUseCase {

    void sendMessage(Long userId, String contents);

    void sendMessage(Long reservationId, Long userId, String contents);
}
