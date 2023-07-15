package backend.wal.notification.adapter;

import backend.wal.notification.application.port.out.NotificationRequestDto;
import backend.wal.notification.application.port.out.NotificationRequestDtos;
import backend.wal.reservation.application.port.in.UpdateReservationUseCase;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.firebase.messaging.BatchResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirebaseAsyncCallbackHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseAsyncCallbackHandler.class);

    private final FirebaseMessageReSender firebaseMessageReSender;
    private final UpdateReservationUseCase updateReservationUseCase;

    public FirebaseAsyncCallbackHandler(final FirebaseMessageReSender firebaseMessageReSender,
                                        final UpdateReservationUseCase updateReservationUseCase) {
        this.firebaseMessageReSender = firebaseMessageReSender;
        this.updateReservationUseCase = updateReservationUseCase;
    }

    public Runnable notificationCallbackHandler(
            ApiFuture<BatchResponse> batchResponseFuture,
            NotificationRequestDtos requestDto) {
        return () -> {
            BatchMessageResponse batchMessageResponse = new BatchMessageResponse(batchResponseFuture);
            if (batchMessageResponse.hasFailure()) {
                LOGGER.info("메세지 전송에 실패했습니다 {}", batchMessageResponse.getFailureReasons(requestDto.getFcmTokens()));
                List<NotificationRequestDto> failure = batchMessageResponse.extractFailure(requestDto.getValues());
                long finalRetryCount = firebaseMessageReSender.retryFailureMessage(failure);
                LOGGER.info("메시지 전송 재시도 횟수 : {}", finalRetryCount);
            }
            LOGGER.info(
                    "메세지 전송을 완료했습니다 성공 : {} 실패 : {}",
                    batchMessageResponse.getSuccessCount(),
                    batchMessageResponse.getFailureCount()
            );
        };
    }

    public ApiFutureCallback<String> reservationNotificationCallbackHandler(Long reservationId) {
        return new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable t) {
                LOGGER.info("예약 메세지 전송에 실패했습니다 {}", t.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                updateReservationUseCase.updateSendStatusToDone(reservationId);
                LOGGER.info("예약 메세지 전송에 성공했습니다 {}", result);
            }
        };
    }
}
