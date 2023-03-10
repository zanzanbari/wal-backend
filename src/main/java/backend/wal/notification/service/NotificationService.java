package backend.wal.notification.service;

import backend.wal.notification.domain.entity.FcmToken;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final FcmTokenService fcmTokenService;

    public void sendDefaultTimeMessage(Long userId, String contents) {
        FcmToken fcmToken = fcmTokenService.findToken(userId);
        Notification notification = Notification.builder()
                .setTitle("πΆμ€λμ κ°μλ¦¬ λμ°©!πΆ")
                .setBody(contents)
                .build();
        Message message = Message.builder()
                .setToken(fcmToken.getValue())
                .setNotification(notification)
                .build();
        ApiFutures.addCallback(
                firebaseMessaging.sendAsync(message),
                getApiFutureCallback(),
                MoreExecutors.directExecutor()
        );
    }

    @NotNull
    private static ApiFutureCallback<String> getApiFutureCallback() {
        return new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable t) {
                log.info(String.format("λ©μΈμ§ μ μ‘μ μ€ν¨νμ΅λλ€ (%s)", t.getMessage()));
            }

            @Override
            public void onSuccess(String result) {
                log.info(String.format("λ©μΈμ§ μ μ‘μ μ±κ³΅νμ΅λλ€ (%s)", result));
            }
        };
    }
}
