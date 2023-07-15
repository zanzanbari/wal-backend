package backend.wal.notification.adapter;

import backend.wal.notification.application.port.out.NotificationRequestDto;

import com.google.firebase.messaging.*;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FirebaseMessageReSender {

    private final FirebaseMessaging firebaseMessaging;
    private final FirebaseMessageCreator firebaseMessageCreator;
    private final FirebaseBackOff firebaseBackOff;

    public FirebaseMessageReSender(final FirebaseMessaging firebaseMessaging,
                                   final FirebaseMessageCreator firebaseMessageCreator,
                                   final FirebaseBackOff firebaseBackOff) {
        this.firebaseMessaging = firebaseMessaging;
        this.firebaseMessageCreator = firebaseMessageCreator;
        this.firebaseBackOff = firebaseBackOff;
    }

    public long retryFailureMessage(List<NotificationRequestDto> failureRequest) {
        List<NotificationRequestDto> failure = new ArrayList<>(failureRequest);
        boolean retry  = true;
        boolean isFailure = true;
        while(retry && isFailure) {
            if (firebaseBackOff.isStopped()) {
                retry = false;
                continue;
            }
            firebaseBackOff.nextBackOffMillis();
            firebaseBackOff.waitUntilInterval();
            failure = resendAndExtractFailure(failure);
            if (failure.isEmpty()) {
                isFailure = false;
            }
        }
        long finalRetryCount = firebaseBackOff.getCurrentRetry();
        firebaseBackOff.reset();
        return finalRetryCount;
    }

    private List<NotificationRequestDto> resendAndExtractFailure(List<NotificationRequestDto> failureRequest) {
        List<Message> messages = firebaseMessageCreator.createMessages(failureRequest);
        BatchMessageResponse batchResponse = new BatchMessageResponse(firebaseMessaging.sendAllAsync(messages));
        if (batchResponse.hasFailure()) {
            return batchResponse.extractFailure(failureRequest);
        }
        return Collections.emptyList();
    }
}
