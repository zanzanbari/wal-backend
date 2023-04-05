package backend.wal.reservation.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationCalendarResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime reserveDate;

    public ReservationCalendarResponse(final LocalDateTime reserveDate) {
        this.reserveDate = reserveDate;
    }
}
