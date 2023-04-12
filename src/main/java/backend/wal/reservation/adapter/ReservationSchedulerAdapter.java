package backend.wal.reservation.adapter;

import backend.wal.reservation.application.port.out.ReservationSchedulerPort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public final class ReservationSchedulerAdapter implements ReservationSchedulerPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationSchedulerAdapter.class);

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final ReservationScheduledFutures reservationScheduledFutures = new ReservationScheduledFutures();

    @Override
    public void sendMessageAfterDelay(Runnable task, long delayTime, Long reservationId) {
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(task, delayTime, TimeUnit.MILLISECONDS);
        reservationScheduledFutures.register(reservationId, scheduledFuture);
        LOGGER.info("예약번호 {}번 등록 완료 - 딜레이 시간 {}", reservationId, delayTime);
    }

    @Override
    public void cancelMessage(Long reservationId) {
        if (reservationScheduledFutures.cancelScheduleByKey(reservationId)) {
            reservationScheduledFutures.removeByKey(reservationId);
            LOGGER.info("예약번호 {}번 취소 완료", reservationId);
        }
    }

    @Override
    public void shoutDown() {
        scheduledExecutorService.shutdown();
    }
}
