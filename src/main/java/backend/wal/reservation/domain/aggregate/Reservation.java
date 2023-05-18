package backend.wal.reservation.domain.aggregate;

import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;
import backend.wal.reservation.domain.view.ReservationHistory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public Reservation(final Long userId, final String message, final LocalDateTime sendDueDate,
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

    public ReservationHistory toHistory() {
        return new ReservationHistory(id, message, sendDueDate, showStatus, sendStatus, createdAt);
    }

    public void setCreatedAtForTest(LocalDateTime testTime) { // FIXME : 다른 방법 없는지 찾아보기
        this.createdAt = testTime;
    }
}
