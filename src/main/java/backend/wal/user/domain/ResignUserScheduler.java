package backend.wal.user.domain;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public final class ResignUserScheduler {

    private final ScheduledExecutorService scheduledExecutorService;

    public ResignUserScheduler() {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void resignAfterDay(long delayOneDayMillis, Runnable task) {
        scheduledExecutorService.schedule(task, delayOneDayMillis, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void shoutDown() {
        scheduledExecutorService.shutdown();
    }
}
