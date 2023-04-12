package backend.wal.notification.adapter;

import backend.wal.notification.application.port.out.FirebaseMessagingPort;
import backend.wal.reservation.application.port.in.UpdateReservationUseCase;

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
    private final UpdateReservationUseCase updateReservationUseCase;

    public FirebaseMessagingAdapter(final FirebaseMessaging firebaseMessaging,
                                    final UpdateReservationUseCase updateReservationUseCase) {
        this.firebaseMessaging = firebaseMessaging;
        this.updateReservationUseCase = updateReservationUseCase;
    }

    @Override
    public void send(String contents, String fcmTokenValue) {
        Message message = createMessage(contents, fcmTokenValue);
        ApiFuture<String> messageStringApiFuture = firebaseMessaging.sendAsync(message);
        ApiFutureCallback<String> apiFutureCallback = registerApiFutureCallback();
        Executor directExecutor = MoreExecutors.directExecutor();
        ApiFutures.addCallback(messageStringApiFuture, apiFutureCallback, directExecutor);
    }

    @NotNull
    private ApiFutureCallback<String> registerApiFutureCallback() {
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

    @Override
    public void send(String contents, String fcmTokenValue, Long reservationId) {
        Message message = createMessage(contents, fcmTokenValue);
        ApiFuture<String> messageStringApiFuture = firebaseMessaging.sendAsync(message);
        ApiFutureCallback<String> apiFutureCallback = registerApiFutureCallback(reservationId);
        Executor directExecutor = MoreExecutors.directExecutor();
        ApiFutures.addCallback(messageStringApiFuture, apiFutureCallback, directExecutor);
    }

    private Message createMessage(String contents, String fcmTokenValue) {
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
    private ApiFutureCallback<String> registerApiFutureCallback(Long reservationId) {
        return new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable t) {
                LOGGER.info(String.format("메세지 전송에 실패했습니다 (%s)", t.getMessage()));
            }

            @Override
            public void onSuccess(String result) {
                updateReservationUseCase.updateSendStatusToDone(reservationId);
                LOGGER.info(String.format("메세지 전송에 성공했습니다 (%s)", result));
            }
        };
    }
}
