package backend.wal.reservation.application.port.out;

public final class NotificationRequestDto {

    private final Long reservationId;
    private final Long userId;
    private final String message;

    public NotificationRequestDto(final Long reservationId, final Long userId, final String message) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.message = message;
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
}
