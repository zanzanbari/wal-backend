package backend.wal.reservation.config;

import backend.wal.support.YamlPropertySourceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableRabbit
@PropertySource(
        value = "classpath:wal-backend-config/application-rabbit.yml",
        factory = YamlPropertySourceFactory.class,
        ignoreResourceNotFound = true)
public class RabbitMQConnectionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConnectionConfig.class);

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private int port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        LOGGER.info("Create RabbitMQ connection factory with : " + username + "@" + host + ":" + port);
        return connectionFactory;
    }
}
