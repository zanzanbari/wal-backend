package backend.wal.user.domain;

import java.util.Timer;
import java.util.TimerTask;

public final class ResignUserTimerTask extends TimerTask {

    private final Timer timer;
    private final Runnable task;

    public ResignUserTimerTask(final Timer timer, final Runnable task) {
        this.timer = timer;
        this.task = task;
    }

    @Override
    public void run() {
        task.run();
        timer.cancel();
    }
}
