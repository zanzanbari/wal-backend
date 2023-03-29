package backend.wal.notification.application.port;

public interface FirebaseMessagingPort {

    void send(String contents, String fcmTokenValue);
}
