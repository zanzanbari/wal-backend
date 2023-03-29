package backend.wal.reservation.application.port.dto;

import backend.wal.rabbitmq.producer.dto.DelayReservationMessage;

import java.time.LocalDateTime;

public final class ReservationNotificationRequestDto {

    private final Long reservationId;
    private final Long userId;
    private final String message;
    private final LocalDateTime sendDueDate;
    private final long delayTime;

    public ReservationNotificationRequestDto(final Long reservationId, final Long userId, final String message,
                                             final LocalDateTime sendDueDate, final long delayTime) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
        this.delayTime = delayTime;
    }

    public DelayReservationMessage toDelayReservationMessage() {
        return new DelayReservationMessage(userId, message, sendDueDate);
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSendDueDate() {
        return sendDueDate;
    }

    public long getDelayTime() {
        return delayTime;
    }
}
