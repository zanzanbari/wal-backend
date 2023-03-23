package backend.wal.user.domain;

import java.util.Date;
import java.util.Timer;

public final class ResignUserScheduler {

    private static final long MILLIS_OF_ONE_DAY = 1000 * 60 * 60 * 24;

    public void resignAfterDay(Runnable task) {
        Timer timer = new Timer();
        ResignUserTimerTask resignUserTimerTask = new ResignUserTimerTask(timer, task);

        long millisOneDayAfterNow = new Date().getTime() + MILLIS_OF_ONE_DAY;
        timer.schedule(resignUserTimerTask, millisOneDayAfterNow);
    }
}
