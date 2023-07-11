package backend.wal.notification.adapter;

import backend.wal.notification.exception.FCMException;

import com.google.api.client.util.BackOff;

import org.springframework.stereotype.Component;

@Component
public class FirebaseBackOff implements BackOff {

    private static final long MIN_RETRY = 0;
    private static final long MAX_RETRY = 3;
    private static final long INITIAL_INTERVAL = 1_000;
    private static final long MAX_INTERVAL = 5_000;

    private Long currentRetry = MIN_RETRY;
    private Long currentInterval = INITIAL_INTERVAL;

    @Override
    public long nextBackOffMillis() {
        if (isStopped()) {
            return MAX_RETRY;
        }
        long exponentialInterval = INITIAL_INTERVAL * (1L << currentRetry);
        long nextInterval = Math.min(exponentialInterval, MAX_INTERVAL);
        currentInterval = nextInterval;
        currentRetry++;
        return nextInterval;
    }

    @Override
    public void reset() {
        currentRetry= MIN_RETRY;
        currentInterval= INITIAL_INTERVAL;
    }

    public boolean isStopped() {
        return currentRetry.equals(MAX_RETRY);
    }

    public void waitUntilInterval() {
        try {
            Thread.sleep(getCurrentInterval());
        } catch (InterruptedException e) {
            throw FCMException.threadInterruptException(e.getMessage());
        }
    }

    public long getCurrentRetry() {
        return currentRetry;
    }

    public long getCurrentInterval() {
        return currentInterval;
    }
}
