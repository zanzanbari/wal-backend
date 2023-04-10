package backend.wal.reservation.application.port.in.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ReservationCalendarResponseDto {

    private final LocalDate reserveDate;

    public ReservationCalendarResponseDto(final LocalDate reserveDate) {
        this.reserveDate = reserveDate;
    }

    public static ReservationCalendarResponseDto create(final LocalDateTime localDateTime) {
        return new ReservationCalendarResponseDto(localDateTime.toLocalDate());
    }
}
