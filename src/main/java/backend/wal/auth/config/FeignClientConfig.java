package backend.wal.auth.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("backend.wal.auth.adapter.oauth")
public class FeignClientConfig {

    @Bean
    Retryer.Default retry() {
        return new Retryer.Default(500L, 2000L, 3);
    }

    @Bean
    Logger.Level feginLoggerLevel() {
        return Logger.Level.HEADERS;
    }
}
