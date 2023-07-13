package backend.wal.notification.adapter;

import com.google.firebase.messaging.*;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirebaseMessageCreator {

    private static final String NOTIFICATION_TITLE = "왈";
    private static final String ALERT_SOUND = "default";

    public MulticastMessage createMulticastMessage(List<String> fcmTokenValues) {
        return MulticastMessage.builder()
                .addAllTokens(fcmTokenValues)
                .setApnsConfig(createAppleNotification(""))
                .build();
    }

    public Message createMessage(String fcmTokenValue, String content) {
        return Message.builder()
                .setToken(fcmTokenValue)
                .setApnsConfig(createAppleNotification(content))
                .build();
    }

    private ApnsConfig createAppleNotification(String content) {
        ApsAlert apsAlert = ApsAlert.builder()
                .setTitle(NOTIFICATION_TITLE)
                .setBody(content)
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
