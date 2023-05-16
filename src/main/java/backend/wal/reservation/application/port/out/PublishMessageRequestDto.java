package backend.wal.reservation.application.port.out;

import backend.wal.reservation.application.port.in.dto.DelayReservationMessageRequestDto;

import java.time.LocalDateTime;

public final class PublishMessageRequestDto {

    private final Long reservationId;
    private final Long userId;
    private final String message;
    private final LocalDateTime sendDueDate;
    private final long delayTime;

    public PublishMessageRequestDto(final Long reservationId, final Long userId, final String message,
                                    final LocalDateTime sendDueDate, final long delayTime) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
        this.delayTime = delayTime;
    }

    public DelayReservationMessageRequestDto toDelayReservationMessageRequestDto() {
        return new DelayReservationMessageRequestDto(reservationId, userId, message, sendDueDate);
    }

    public Long getReservationId() {
        return reservationId;
    }

    public long getDelayTime() {
        return delayTime;
    }
}
