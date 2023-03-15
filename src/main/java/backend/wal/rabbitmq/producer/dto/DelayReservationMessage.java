package backend.wal.rabbitmq.producer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DelayReservationMessage {

    private Long userId;
    private String message;
    private LocalDateTime sendDueDate;

    public DelayReservationMessage(final Long userId, final String message, final LocalDateTime sendDueDate) {
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
    }
}
