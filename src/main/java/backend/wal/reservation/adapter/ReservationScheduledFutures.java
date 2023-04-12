package backend.wal.reservation.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public final class ReservationScheduledFutures {

    private final Map<Long, ScheduledFuture<?>> values = new HashMap<>();

    public void register(Long key, ScheduledFuture<?> scheduledFuture) {
        values.put(key, scheduledFuture);
    }

    public boolean cancelScheduleByKey(Long key) {
        ScheduledFuture<?> scheduledFuture = findByKey(key);
        return scheduledFuture.cancel(true);
    }

    public ScheduledFuture<?> findByKey(Long key) {
        return values.get(key);
    }

    public void removeByKey(Long key) {
        values.remove(key);
    }
}
