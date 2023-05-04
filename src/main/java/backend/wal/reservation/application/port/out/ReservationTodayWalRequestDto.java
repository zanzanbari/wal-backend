package backend.wal.reservation.application.port.out;

import java.time.LocalDateTime;

public final class ReservationTodayWalRequestDto {

    private final Long userId;
    private final String message;
    private final LocalDateTime sendTime;

    public ReservationTodayWalRequestDto(final Long userId, final String message, final LocalDateTime sendTime) {
        this.userId = userId;
        this.message = message;
        this.sendTime = sendTime;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }
}
