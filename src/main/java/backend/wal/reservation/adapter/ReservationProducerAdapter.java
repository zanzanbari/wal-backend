package backend.wal.reservation.adapter;

import backend.wal.reservation.application.port.in.dto.DelayReservationMessageRequestDto;
import backend.wal.reservation.application.port.out.PublishMessageRequestDto;
import backend.wal.reservation.application.port.out.ReservationProducerPort;
import backend.wal.reservation.exception.InternalServerJsonException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public final class ReservationProducerAdapter implements ReservationProducerPort {

    private final RabbitTemplate reservationRabbitTemplate;

    public ReservationProducerAdapter(final RabbitTemplate reservationRabbitTemplate) {
        this.reservationRabbitTemplate = reservationRabbitTemplate;
    }

    @Override
    public void sendMessageToQueue(PublishMessageRequestDto requestDto) {
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader(MessageProperties.X_DELAY, requestDto.getDelayTime())
                .setMessageId(String.valueOf(requestDto.getReservationId()))
                .build();

        String jsonMessage = parseToJson(requestDto.toDelayReservationMessageRequestDto());
        Message message = MessageBuilder.withBody(jsonMessage.getBytes())
                .andProperties(messageProperties)
                .build();

        reservationRabbitTemplate.convertAndSend(message);
    }

    private String parseToJson(DelayReservationMessageRequestDto delayReservationMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(delayReservationMessage);
        } catch (JsonProcessingException e) {
            throw InternalServerJsonException.fail(e.getMessage());
        }
    }
}
