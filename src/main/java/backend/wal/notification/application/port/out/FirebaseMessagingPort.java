package backend.wal.notification.application.port.out;

public interface FirebaseMessagingPort {

    void send(Long reservationId, String contents, String fcmTokenValue);
}
