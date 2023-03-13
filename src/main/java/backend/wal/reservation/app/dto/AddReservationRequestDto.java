package backend.wal.reservation.app.dto;

import backend.wal.reservation.domain.aggregate.vo.ShowStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public final class AddReservationRequestDto {

    private final Long userId;
    private final String message;
    private final LocalDate sendDate;
    private final LocalTime sendTime;
    private final ShowStatus showStatus;

    private AddReservationRequestDto(final Long userId, final String message, final LocalDate sendDate,
                                    final LocalTime sendTime, final ShowStatus showStatus) {
        this.userId = userId;
        this.message = message;
        this.sendDate = sendDate;
        this.sendTime = sendTime;
        this.showStatus = showStatus;
    }

    public static AddReservationRequestDto of(final Long userId, final String message, final String sendDate,
                                              final String sendTime, final ShowStatus showStatus) {
        return new AddReservationRequestDto(
                userId,
                message,
                LocalDate.parse(sendDate),
                LocalTime.parse(sendTime),
                showStatus
        );
    }
}
