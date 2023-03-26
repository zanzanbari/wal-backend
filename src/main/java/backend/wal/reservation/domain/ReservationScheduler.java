package backend.wal.reservation.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

@Component
public final class ReservationScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationScheduler.class);

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final Map<Long, ScheduledFuture<?>> reservationScheduledFutures = new ConcurrentHashMap<>();

    public void sendMessageAfterDelay(Runnable task, long delayTime, Long reservationId) {
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(task, delayTime, MILLISECONDS);
        reservationScheduledFutures.put(reservationId, scheduledFuture);
        LOGGER.debug("예약번호 {}번 등록 완료 - 딜레이 시간 {}", reservationId, delayTime);
    }

    public void cancelMessage(Long reservationId) {
        ScheduledFuture<?> scheduledFuture = reservationScheduledFutures.get(reservationId);
        scheduledFuture.cancel(true);
        reservationScheduledFutures.remove(reservationId);
        LOGGER.debug("예약번호 {}번 취소 완료 - 남은 딜레이 시간 {}", reservationId, scheduledFuture.getDelay(MILLISECONDS));
    }

    public void shoutDown() {
        scheduledExecutorService.shutdown();
    }
}
