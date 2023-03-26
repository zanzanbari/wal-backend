package backend.wal.reservation.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public final class ReservationScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationScheduler.class);

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final ReservationScheduledFutures reservationScheduledFutures = new ReservationScheduledFutures();

    public void sendMessageAfterDelay(Runnable task, long delayTime, Long reservationId) {
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(task, delayTime, TimeUnit.MILLISECONDS);
        reservationScheduledFutures.register(reservationId, scheduledFuture);
        LOGGER.debug("예약번호 {}번 등록 완료 - 딜레이 시간 {}", reservationId, delayTime);
    }

    public void cancelMessage(Long reservationId) {
        if (reservationScheduledFutures.cancelScheduleByKey(reservationId)) {
            reservationScheduledFutures.removeByKey(reservationId);
            LOGGER.debug("예약번호 {}번 취소 완료", reservationId);
        }
    }

    public void shoutDown() {
        scheduledExecutorService.shutdown();
    }
}
