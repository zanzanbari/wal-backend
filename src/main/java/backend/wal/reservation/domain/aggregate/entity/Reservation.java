package backend.wal.reservation.domain.aggregate.entity;

import backend.wal.reservation.domain.aggregate.vo.SendStatus;
import backend.wal.reservation.domain.aggregate.vo.ShowStatus;
import backend.wal.reservation.app.dto.AddReservationRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
}
