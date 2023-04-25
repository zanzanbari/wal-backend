package backend.wal.reservation.domain.aggregate;

import backend.wal.reservation.application.port.in.dto.ReservationHistoryResponseDto;
import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime sendDueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ShowStatus showStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SendStatus sendStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    private Reservation(final Long userId, final String message, final LocalDateTime sendDueDate,
                       final ShowStatus showStatus, final SendStatus sendStatus) {
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
        this.showStatus = showStatus;
        this.sendStatus = sendStatus;
    }

    public static Reservation newInstance(final AddReservationRequestDto requestDto) {
        LocalDateTime sendDueDate = LocalDateTime.of(requestDto.getSendDate(), requestDto.getSendTime());
        return new Reservation(
                requestDto.getUserId(),
                requestDto.getMessage(),
                sendDueDate,
                requestDto.getShowStatus(),
                SendStatus.NOT_DONE
        );
    }

    public boolean isToday(LocalDate today) {
        return sendDueDate.toLocalDate()
                .isEqual(today);
    }

    public long getDelayTimeAboutNow(LocalDateTime now) {
        return Duration.between(now, sendDueDate)
                .toMillis();
    }

    public void finish() {
        this.sendStatus = SendStatus.DONE;
    }

    public boolean isDone() {
        return sendStatus == SendStatus.DONE;
    }

    public boolean isNotDone() {
        return sendStatus == SendStatus.NOT_DONE;
    }

    public ReservationHistoryResponseDto getDetailSendDateInfo() {
        String monthDate = sendDueDate.format(DateTimeFormatter.ofPattern("MM. dd"));
        String time = sendDueDate.format(DateTimeFormatter.ofPattern(":mm"));

        int hour = sendDueDate.getHour();
        if (hour > 12) {
            time = "오후 " + (hour - 12) + time;
        } else {
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
        return new ReservationHistoryResponseDto(
                id,
                message,
                detailMessage,
                showStatus,
                createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm"))
        );
    }
}
