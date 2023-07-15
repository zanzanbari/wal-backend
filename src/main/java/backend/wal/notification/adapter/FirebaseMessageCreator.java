package backend.wal.notification.adapter;

import backend.wal.notification.application.port.out.NotificationRequestDto;

import com.google.firebase.messaging.*;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FirebaseMessageCreator {

    private static final String NOTIFICATION_TITLE = "왈";
    private static final String ALERT_SOUND = "default";

    public List<Message> createMessages(List<NotificationRequestDto> requestDtos) {
        return requestDtos.stream()
                .map(requestDto -> createMessage(requestDto.getFcmToken(), requestDto.getContents()))
                .collect(Collectors.toUnmodifiableList());
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
