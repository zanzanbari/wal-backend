package backend.wal.reservation.application.port.in;

import backend.wal.reservation.application.port.in.dto.DelayReservationMessageRequestDto;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public interface ReservationConsumerUseCase {

    @RabbitListener(queues = "reservation.queue")
    void consumeMessageFromQueue(DelayReservationMessageRequestDto requestDto);
}
