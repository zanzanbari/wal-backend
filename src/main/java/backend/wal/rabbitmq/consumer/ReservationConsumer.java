package backend.wal.rabbitmq.consumer;

import backend.wal.notification.service.NotificationService;
import backend.wal.rabbitmq.producer.dto.DelayReservationMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReservationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationConsumer.class);

    private final NotificationService notificationService;

    @RabbitListener(queues = "reservation.queue")
    public void consumeMessageFromQueue(DelayReservationMessage reservationMessage) {
        LOGGER.debug("예약 메세지(등록 시간: {})를 reservation.queue 로부터 가져왔습니다 {}",
                reservationMessage.getSendDueDate(),
                LocalDateTime.now());
        notificationService.sendMessage(reservationMessage.getUserId(), reservationMessage.getMessage());
    }
}
