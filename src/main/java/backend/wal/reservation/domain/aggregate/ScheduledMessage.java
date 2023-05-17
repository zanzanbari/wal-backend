package backend.wal.reservation.domain.aggregate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class ScheduledMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservationId;

    private Long userId;

    private String message;

    private LocalDateTime sendDueDate;

    protected ScheduledMessage() {
    }

    public ScheduledMessage(final Long reservationId, final Long userId,
                            final String message, final LocalDateTime sendDueDate) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
    }

    public static ScheduledMessage newInstance(final Reservation reservation) {
        return new ScheduledMessage(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getMessage(),
                reservation.getSendDueDate()
        );
    }

    public long getDelayTimeAboutNow(LocalDateTime now) {
        return Duration.between(now, sendDueDate)
                .toMillis();
    }

    public Long getId() {
        return id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSendDueDate() {
        return sendDueDate;
    }
}
