package backend.wal.reservation.app.dto;

import backend.wal.rabbitmq.producer.dto.PublishReservationRequestDto;

import java.time.LocalDateTime;

public final class RegisterReservationResponseDto {

    private final Long userId;
    private final String message;
    private final LocalDateTime sendDueDate;
    private final long delayTime;

    public RegisterReservationResponseDto(final Long userId, final String message,
                                          final LocalDateTime sendDueDate, final long delayTime) {
        this.userId = userId;
        this.message = message;
        this.sendDueDate = sendDueDate;
        this.delayTime = delayTime;
    }

    public PublishReservationRequestDto toPublishRequestDto() {
        return new PublishReservationRequestDto(userId, message, sendDueDate, delayTime);
    }
}
