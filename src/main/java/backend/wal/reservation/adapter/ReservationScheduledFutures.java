package backend.wal.reservation.adapter;

import backend.wal.reservation.exception.NotFoundReservationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

public final class ReservationScheduledFutures {

    private final Map<Long, ScheduledFuture<?>> values = new HashMap<>();

    public void register(Long key, ScheduledFuture<?> scheduledFuture) {
        values.put(key, scheduledFuture);
    }

    public boolean cancelScheduleByKey(Long key) {
        ScheduledFuture<?> scheduledFuture = findByKey(key)
                .orElseThrow(() -> NotFoundReservationException.notExists(key));
        return scheduledFuture.cancel(true);
    }

    public Optional<ScheduledFuture<?>> findByKey(Long key) {
        return Optional.ofNullable(values.get(key));
    }

    public void removeByKey(Long key) {
        values.remove(key);
    }
}
