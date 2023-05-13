package backend.wal.reservation.application.port.in.dto;

public final class ReservationHistoryResponseDto {

    private final Long reservationId;
    private final String message;
    private final String detail;
    private final String showStatus;
    private final String reservedAt;
    private final String sendingDate;

    public ReservationHistoryResponseDto(final Long reservationId, final String message, final String detail,
                                         final String showStatus, final String reservedAt, final String sendingDate) {
        this.reservationId = reservationId;
        this.message = message;
        this.detail = detail;
        this.showStatus = showStatus;
        this.reservedAt = reservedAt;
        this.sendingDate = sendingDate;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public String getReservedAt() {
        return reservedAt;
    }

    public String getSendingDate() {
        return sendingDate;
    }
}
