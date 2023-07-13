package backend.wal.notification.application.port.out;

import java.util.List;

public interface FirebaseMessagingPort {

    void sendMessage(List<String> fcmTokenValues);

    void sendReservation(String fcmTokenValue, Long reservationId, String content);
}
