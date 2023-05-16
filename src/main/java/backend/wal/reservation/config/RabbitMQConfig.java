package backend.wal.reservation.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String QUEUE_NAME = "reservation.queue";
    private static final String EXCHANGE_NAME = "reservation.delayed.exchange";
    private static final String ROUTING_KEY = "reservation.key";

    @Bean
    public RabbitTemplate reservationRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        rabbitTemplate.setRoutingKey(ROUTING_KEY);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue reservationQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .build();
    }

    @Bean
    public Exchange reservationExchange() {
        return new DelayedMessageExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue reservationQueue, Exchange reservationExchange) {
        return BindingBuilder.bind(reservationQueue)
                .to(reservationExchange)
                .with(ROUTING_KEY)
                .noargs();
    }
}
