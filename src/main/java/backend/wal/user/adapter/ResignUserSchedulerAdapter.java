package backend.wal.user.adapter;

import backend.wal.user.application.port.out.ResignUserSchedulerPort;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public final class ResignUserSchedulerAdapter implements ResignUserSchedulerPort {

    private final ScheduledExecutorService scheduledExecutorService =  Executors.newSingleThreadScheduledExecutor();

    public void resignAfterDay(Runnable task, long delayOneDayMillis) {
        scheduledExecutorService.schedule(task, delayOneDayMillis, TimeUnit.MILLISECONDS);
    }

    public void shoutDown() {
        scheduledExecutorService.shutdown();
    }
}
