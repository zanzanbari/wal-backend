package backend.wal.reservation.app.dto;

import backend.wal.reservation.controller.dto.ReservationCalendarResponse;

import java.time.LocalDateTime;

public final class ReservationCalendarResponseDto {

    private final LocalDateTime reserveDate;

    public ReservationCalendarResponseDto(final LocalDateTime reserveDate) {
        this.reserveDate = reserveDate;
    }

    public ReservationCalendarResponse toWebResponse() {
        return new ReservationCalendarResponse(reserveDate);
    }
}
