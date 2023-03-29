package backend.wal.user.application.port;

public interface ResignUserSchedulerPort {

    void resignAfterDay(Runnable task, long delayOneDayMillis);

    void shoutDown();
}
