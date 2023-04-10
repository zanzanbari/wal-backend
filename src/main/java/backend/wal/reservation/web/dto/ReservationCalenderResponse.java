package backend.wal.reservation.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ReservationCalendarResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDateTime reserveDate;

    public ReservationCalendarResponse(final LocalDateTime reserveDate) {
        this.reserveDate = reserveDate;
    }

    public LocalDateTime getReserveDate() {
        return reserveDate;
    }
}
