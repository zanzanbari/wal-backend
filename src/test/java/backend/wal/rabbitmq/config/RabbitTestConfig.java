package backend.wal.rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@TestConfiguration
@RabbitListenerTest(capture = true)
public class RabbitTestConfig {

    @Bean
    public TestRabbitTemplate testRabbitTemplate() {
        return new TestRabbitTemplate(mockConnectionFactory());
    }

    @Bean
    public ConnectionFactory mockConnectionFactory() {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        Channel channel = mock(Channel.class);
        willReturn(connection).given(connectionFactory).createConnection();
        willReturn(channel).given(connection).createChannel(anyBoolean());
        given(channel.isOpen()).willReturn(true);
        return connectionFactory;
    }
}
