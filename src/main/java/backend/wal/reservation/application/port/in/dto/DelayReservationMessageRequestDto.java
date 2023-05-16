package backend.wal.reservation.application.port.in.dto;

import backend.wal.reservation.application.port.out.NotificationRequestDto;

import java.time.LocalDateTime;

public final class DelayReservationMessageRequestDto {

    private final Long reservationId;
    private final Long userId;
    private final String message;
    private final LocalDateTime sendDueDate;

    public DelayReservationMessageRequestDto(final Long reservationId, final Long userId,
                                             final String message, final LocalDateTime sendDueDate) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
    }

    public NotificationRequestDto toNotificationRequest() {
        return new NotificationRequestDto(reservationId, userId, message);
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
}
