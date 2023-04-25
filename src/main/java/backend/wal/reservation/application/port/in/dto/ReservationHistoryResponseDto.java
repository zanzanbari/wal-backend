package backend.wal.reservation.application.port.in.dto;

import backend.wal.reservation.domain.aggregate.ShowStatus;

public final class ReservationHistoryResponseDto {

    private final Long reservationId;
    private final String message;
    private final String detail;
    private final ShowStatus showStatus;
    private final String reservedAt;

    public ReservationHistoryResponseDto(final Long reservationId, final String message, final String detail,
                                         final ShowStatus showStatus, final String reservedAt) {
        this.reservationId = reservationId;
        this.message = message;
        this.detail = detail;
        this.showStatus = showStatus;
        this.reservedAt = reservedAt;
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

    public ShowStatus getShowStatus() {
        return showStatus;
    }

    public String getReservedAt() {
        return reservedAt;
    }
}
