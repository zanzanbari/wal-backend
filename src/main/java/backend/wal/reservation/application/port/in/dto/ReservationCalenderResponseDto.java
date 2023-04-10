package backend.wal.reservation.application.port.in.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ReservationCalenderResponseDto {

    private final LocalDate reserveDate;

    public ReservationCalenderResponseDto(final LocalDate reserveDate) {
        this.reserveDate = reserveDate;
    }

    public static ReservationCalenderResponseDto create(final LocalDateTime localDateTime) {
        return new ReservationCalenderResponseDto(localDateTime.toLocalDate());
    }

    public LocalDate getReserveDate() {
        return reserveDate;
    }
}
