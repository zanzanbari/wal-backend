package backend.wal.notification.adapter;

import backend.wal.notification.application.port.out.FirebaseMessagingPort;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public final class FirebaseMessagingAdapter implements FirebaseMessagingPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseMessagingAdapter.class);

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingAdapter(final FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Override
    public void send(String contents, String fcmTokenValue) {
        Message message = buildMessage(contents, fcmTokenValue);
        ApiFuture<String> messageStringApiFuture = firebaseMessaging.sendAsync(message);
        ApiFutureCallback<String> apiFutureCallback = getApiFutureCallback();
        Executor directExecutor = MoreExecutors.directExecutor();
        ApiFutures.addCallback(messageStringApiFuture, apiFutureCallback, directExecutor);
    }

    private Message buildMessage(String contents, String fcmTokenValue) {
        Notification notification = Notification.builder()
                .setTitle("🐶오늘의 개소리 도착!🐶")
                .setBody(contents)
                .build();
        return Message.builder()
                .setToken(fcmTokenValue)
                .setNotification(notification)
                .build();
    }

    @NotNull
    private ApiFutureCallback<String> getApiFutureCallback() {
        return new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable t) {
                LOGGER.info(String.format("메세지 전송에 실패했습니다 (%s)", t.getMessage()));
            }

            @Override
            public void onSuccess(String result) {
                LOGGER.info(String.format("메세지 전송에 성공했습니다 (%s)", result));
            }
        };
    }
}
