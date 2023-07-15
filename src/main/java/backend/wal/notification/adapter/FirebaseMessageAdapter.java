package backend.wal.notification.adapter;

import backend.wal.notification.application.port.out.FirebaseMessagingPort;

import backend.wal.notification.application.port.out.NotificationRequestDtos;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

@Component
public class FirebaseMessageAdapter implements FirebaseMessagingPort {

    private final FirebaseMessaging firebaseMessaging;
    private final FirebaseMessageCreator firebaseMessageCreator;
    private final FirebaseAsyncCallbackHandler firebaseAsyncCallbackHandler;

    public FirebaseMessageAdapter(final FirebaseMessaging firebaseMessaging,
                                  final FirebaseMessageCreator firebaseMessageCreator,
                                  final FirebaseAsyncCallbackHandler firebaseAsyncCallbackHandler) {
        this.firebaseMessaging = firebaseMessaging;
        this.firebaseMessageCreator = firebaseMessageCreator;
        this.firebaseAsyncCallbackHandler = firebaseAsyncCallbackHandler;
    }

    @Override
    public void sendMessage(NotificationRequestDtos requestDtos) {
        List<Message> messages = firebaseMessageCreator.createMessages(requestDtos.getValues());
        ApiFuture<BatchResponse> messageFuture = firebaseMessaging.sendAllAsync(messages);
        Runnable callbackHandler = firebaseAsyncCallbackHandler
                .notificationCallbackHandler(messageFuture, requestDtos);
        Executor directExecutor = MoreExecutors.directExecutor();
        messageFuture.addListener(callbackHandler, directExecutor);
    }

    @Override
    public void sendReservation(String fcmTokenValue, Long reservationId, String content) {
        Message message = firebaseMessageCreator.createMessage(fcmTokenValue, content);
        ApiFuture<String> reservationFuture = firebaseMessaging.sendAsync(message);
        ApiFutureCallback<String> callbackHandler = firebaseAsyncCallbackHandler
                .reservationNotificationCallbackHandler(reservationId);
        Executor directExecutor = MoreExecutors.directExecutor();
        ApiFutures.addCallback(reservationFuture, callbackHandler, directExecutor);
    }
}
