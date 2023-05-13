package backend.wal.reservation.domain.view;

import backend.wal.reservation.application.port.in.dto.ReservationHistoryResponseDto;
import backend.wal.reservation.domain.aggregate.SendStatus;
import backend.wal.reservation.domain.aggregate.ShowStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public final class ReservationHistory {

    private final Long reservationId;
    private final String message;
    private final LocalDateTime sendDueDate;
    private final ShowStatus showStatus;
    private final SendStatus sendStatus;
    private final LocalDateTime reservedAt;

    public ReservationHistory(final Long reservationId, final String message, final LocalDateTime sendDueDate,
                              final ShowStatus showStatus, final SendStatus sendStatus, final LocalDateTime reservedAt) {
        this.reservationId = reservationId;
        this.message = message;
        this.sendDueDate = sendDueDate;
        this.showStatus = showStatus;
        this.sendStatus = sendStatus;
        this.reservedAt = reservedAt;
    }

    public ReservationHistoryResponseDto getDetailSendDateInfo() {
        String monthDate = sendDueDate.format(DateTimeFormatter.ofPattern("MM.dd"));
        String time = sendDueDate.format(DateTimeFormatter.ofPattern(":mm"));
        int hour = sendDueDate.getHour();

        if (hour > 12) {
            time = "오후 " + (hour - 12) + time;
        }
        else if (hour == 12) {
            time = "오후 " + hour + time;
        }
        else {
            time = "오전 " + hour + time;
        }

        String dayOfWeek = sendDueDate.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String detailMessage = makeDetailMessage(monthDate, time, dayOfWeek);

        return toHistoryResponseDto(detailMessage);
    }

    private String makeDetailMessage(String monthDate, String time, String dayOfWeek) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(monthDate)
                .append(" ")
                .append(time)
                .append(" ")
                .append(dayOfWeek)
                .append(" • ")
                .append(sendStatus.getValue());
        return stringBuilder.toString();
    }

    private ReservationHistoryResponseDto toHistoryResponseDto(String detailMessage) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm");
        return new ReservationHistoryResponseDto(
                reservationId,
                message,
                detailMessage,
                showStatus.name(),
                reservedAt.format(dateTimeFormatter),
                sendDueDate.format(dateTimeFormatter)
        );
    }
}
