package backend.wal.notification.adapter;

import backend.wal.notification.application.port.out.NotificationRequestDto;
import backend.wal.notification.exception.FCMException;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.SendResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BatchMessageResponse {

    private final BatchResponse response;

    public BatchMessageResponse(final ApiFuture<BatchResponse> response) {
        this.response = getBatchResponse(response);
    }

    private static BatchResponse getBatchResponse(ApiFuture<BatchResponse> response) {
        try {
            return response.get();
        } catch (InterruptedException | ExecutionException e) {
            throw FCMException.sendAsyncException(e.getMessage());
        }
    }

    public boolean hasFailure() {
        return response.getFailureCount() > 0;
    }

    public Map<String, String> getFailureReasons(List<String> fcmTokenValues) {
        List<SendResponse> sendResponses = response.getResponses();
        Map<String, String> failureTokenAndReasons = new HashMap<>();
        for (int i = 0; i < sendResponses.size(); i++) {
            SendResponse sendResponse = sendResponses.get(i);
            if (!sendResponse.isSuccessful()) {
                failureTokenAndReasons.put(
                        fcmTokenValues.get(i).substring(0, 3),
                        sendResponse.getException().getMessage());
            }
        }
        return failureTokenAndReasons;
    }

    public List<NotificationRequestDto> extractFailure(List<NotificationRequestDto> requestDto) {
        List<SendResponse> sendResponses = response.getResponses();
        List<NotificationRequestDto> failure = new ArrayList<>();
        for (int i = 0; i < sendResponses.size(); i++) {
            SendResponse sendResponse = sendResponses.get(i);
            if (!sendResponse.isSuccessful()) {
                failure.add(requestDto.get(i));
            }
        }
        return failure;
    }

    public int getFailureCount() {
        return response.getFailureCount();
    }

    public int getSuccessCount() {
        return response.getSuccessCount();
    }
}
