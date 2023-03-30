package backend.wal.reservation.application.port.in.dto;

import backend.wal.reservation.web.dto.ReservationCalendarResponse;

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
