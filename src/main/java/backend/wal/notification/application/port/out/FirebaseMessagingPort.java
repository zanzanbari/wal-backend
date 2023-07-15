package backend.wal.notification.application.port.out;

public interface FirebaseMessagingPort {

    void sendMessage(NotificationRequestDtos requestDtos);

    void sendReservation(String fcmTokenValue, Long reservationId, String content);
}
