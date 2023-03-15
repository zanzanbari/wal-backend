package backend.wal.rabbitmq.producer;

import backend.wal.rabbitmq.exception.InternalServerJsonException;
import backend.wal.rabbitmq.producer.dto.DelayReservationMessage;
import backend.wal.rabbitmq.producer.dto.PublishReservationRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationProducer {

    private static final String DELAY_HEADER = "x-delay";

    private final RabbitTemplate rabbitTemplate;

    public void publishToReservationQueue(PublishReservationRequestDto requestDto) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(DELAY_HEADER, requestDto.getDelayTime());

        String jsonMessage = parseToJson(requestDto.toDelayReservationMessage());
        Message message = new Message(jsonMessage.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(message);
    }

    private static String parseToJson(DelayReservationMessage delayReservationMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(delayReservationMessage);
        } catch (JsonProcessingException e) {
            throw InternalServerJsonException.fail(e.getMessage());
        }
    }
}
