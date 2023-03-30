package backend.wal.reservation.application.port.in.dto;

import backend.wal.reservation.domain.aggregate.ShowStatus;

public final class ReservationHistoryResponseDto {

    private final Long reservationId;
    private final String message;
    private final String detail;
    private final ShowStatus showStatus;

    public ReservationHistoryResponseDto(final Long reservationId, final String message, final String detail,
                                         final ShowStatus showStatus) {
        this.reservationId = reservationId;
        this.message = message;
        this.detail = detail;
        this.showStatus = showStatus;
    }
}
