package backend.wal.reservation.application.port.out;

public final class NotificationRequestDto {

    private final Long reservationId;
    private final Long userId;

    public NotificationRequestDto(final Long reservationId, final Long userId) {
        this.reservationId = reservationId;
        this.userId = userId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getUserId() {
        return userId;
    }
}
