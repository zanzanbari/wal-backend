package backend.wal.reservation.application.port.dto;

import java.time.LocalDateTime;

public final class RegisterReservationResponseDto {

    private final Long reservationId;
    private final Long userId;
    private final String message;
    private final LocalDateTime sendDueDate;
    private final long delayTime;

    public RegisterReservationResponseDto(final Long reservationId, final Long userId, final String message,
                                          final LocalDateTime sendDueDate, final long delayTime) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
        this.delayTime = delayTime;
    }

    public ReservationNotificationRequestDto toPublishRequestDto() {
        return new ReservationNotificationRequestDto(reservationId, userId, message, sendDueDate, delayTime);
    }
}
