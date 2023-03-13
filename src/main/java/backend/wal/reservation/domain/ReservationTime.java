package backend.wal.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ReservationTime {

    private static final long ONE_DAY = 1;

    private final LocalDateTime reservationDate;

    private ReservationTime(final LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public static ReservationTime startDayFrom(final LocalDate localDate) {
        return new ReservationTime(localDate.atStartOfDay());
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public LocalDateTime getNextDate() {
        return reservationDate.plusDays(ONE_DAY);
    }
}
