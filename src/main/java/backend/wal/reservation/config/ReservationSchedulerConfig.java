package backend.wal.reservation.config;

import backend.wal.reservation.adapter.ReservationScheduledFutures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ReservationSchedulerConfig {

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public ReservationScheduledFutures reservationScheduledFutures() {
        return new ReservationScheduledFutures();
    }
}
