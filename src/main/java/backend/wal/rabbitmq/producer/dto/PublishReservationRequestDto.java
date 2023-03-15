package backend.wal.rabbitmq.producer.dto;

import java.time.LocalDateTime;

public final class PublishReservationRequestDto {

    private final Long userId;
    private final String message;
    private final LocalDateTime sendDueDate;
    private final long delayTime;

    public PublishReservationRequestDto(final Long userId, final String message,
                                        final LocalDateTime sendDueDate, final long delayTime) {
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
        this.delayTime = delayTime;
    }

    public DelayReservationMessage toDelayReservationMessage() {
        return new DelayReservationMessage(userId, message, sendDueDate);
    }

    public long getDelayTime() {
        return delayTime;
    }
}
