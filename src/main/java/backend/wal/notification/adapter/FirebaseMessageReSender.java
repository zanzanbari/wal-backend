package backend.wal.notification.adapter;

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

    public int retryFailureMessage(List<String> failureTokenValues) {
        List<String> failure = new ArrayList<>(failureTokenValues);
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
        int finalRetryCount = firebaseBackOff.getCurrentRetry();
        firebaseBackOff.reset();
        return finalRetryCount;
    }

    private List<String> resendAndExtractFailure(List<String> failureTokenValues) {
        MulticastMessage message = firebaseMessageCreator.createMulticastMessage(failureTokenValues);
        BatchMessageResponse batchResponse = new BatchMessageResponse(firebaseMessaging.sendMulticastAsync(message));
        if (batchResponse.hasFailure()) {
            return batchResponse.extractFailure(failureTokenValues);
        }
        return Collections.emptyList();
    }
}
