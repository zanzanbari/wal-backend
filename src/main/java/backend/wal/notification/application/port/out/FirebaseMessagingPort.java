package backend.wal.notification.application.port.out;

public interface FirebaseMessagingPort {

    void send(String contents, String fcmTokenValue);

    void send(String contents, String fcmTokenValue, Long reservationId);
}
