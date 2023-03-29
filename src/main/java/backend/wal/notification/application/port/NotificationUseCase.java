package backend.wal.notification.application.port;

public interface NotificationUseCase {

    void sendMessage(Long userId, String contents);
}
