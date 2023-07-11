package backend.wal.reservation.application.port.in.dto;

import backend.wal.reservation.application.port.out.NotificationRequestDto;

import com.google.common.base.Objects;

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

    public NotificationRequestDto toNotificationRequest() {
        return new NotificationRequestDto(reservationId, userId);
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationNotificationRequestDto that = (ReservationNotificationRequestDto) o;
        return Objects.equal(reservationId, that.reservationId) && Objects.equal(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reservationId, userId);
    }
}
