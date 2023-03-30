package backend.wal.user.application.port.out;

public interface ResignUserSchedulerPort {

    void resignAfterDay(Runnable task, long delayOneDayMillis);

    void shoutDown();
}
