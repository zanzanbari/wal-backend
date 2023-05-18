package backend.wal.reservation.adapter;

import backend.wal.reservation.application.port.out.ReservationSchedulerPort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public final class ReservationSchedulerAdapter implements ReservationSchedulerPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationSchedulerAdapter.class);

    private final ScheduledExecutorService scheduledExecutorService;
    private final ReservationScheduledFutures reservationScheduledFutures;

    public ReservationSchedulerAdapter(final ScheduledExecutorService scheduledExecutorService,
                                       final ReservationScheduledFutures reservationScheduledFutures) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.reservationScheduledFutures = reservationScheduledFutures;
    }

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

    @PreDestroy
    public void shutdown() {
        scheduledExecutorService.shutdown();
        LOGGER.info("예약 메시지 스케줄러 종료");
    }
}
