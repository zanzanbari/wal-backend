package backend.wal.reservation.application.port.out;

public final class NotificationRequestDto {

    private final Long reservationId;
    private final Long userId;
    private final String content;

    public NotificationRequestDto(final Long reservationId, final Long userId, final String content) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.content = content;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
