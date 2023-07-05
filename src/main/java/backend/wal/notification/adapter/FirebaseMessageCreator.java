package backend.wal.notification.adapter;

import com.google.firebase.messaging.*;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirebaseMessageCreator {

    private static final String NOTIFICATION_TITLE = "왈";
    private static final String NOTIFICATION_BODY = "왈소리가 도착했어요!";
    private static final String ALERT_SOUND = "default";

    public MulticastMessage createMulticastMessage(List<String> fcmTokenValues) {
        return MulticastMessage.builder()
                .addAllTokens(fcmTokenValues)
                .setApnsConfig(createAppleNotification())
                .build();
    }

    public Message createMessage(String fcmTokenValue) {
        return Message.builder()
                .setToken(fcmTokenValue)
                .setApnsConfig(createAppleNotification())
                .build();
    }

    private ApnsConfig createAppleNotification() {
        ApsAlert apsAlert = ApsAlert.builder()
                .setTitle(NOTIFICATION_TITLE)
                .setBody(NOTIFICATION_BODY)
                .build();
        Aps aps = Aps.builder()
                .setAlert(apsAlert)
                .setContentAvailable(true)
                .setSound(ALERT_SOUND)
                .build();
        return ApnsConfig.builder()
                .setAps(aps)
                .build();
    }
}
