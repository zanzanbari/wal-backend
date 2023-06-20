package backend.wal.user.adapter;

import backend.wal.user.application.port.out.ResignUserSchedulerPort;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Deprecated(since = "issue.110")
@Component
public final class ResignUserSchedulerAdapter implements ResignUserSchedulerPort {

    private final ScheduledExecutorService scheduledExecutorService =  Executors.newSingleThreadScheduledExecutor();

    @Override
    public void resignAfterDay(Runnable task, long delayOneDayMillis) {
        scheduledExecutorService.schedule(task, delayOneDayMillis, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void shutDown() {
        scheduledExecutorService.shutdown();
    }
}
