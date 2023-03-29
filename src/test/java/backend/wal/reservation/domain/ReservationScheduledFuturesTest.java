package backend.wal.reservation.domain;

import backend.wal.reservation.adapter.ReservationScheduledFutures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReservationScheduledFuturesTest {

    private static final Long RESERVATION_KEY = 1L;

    private final ReservationScheduledFutures reservationScheduledFutures = new ReservationScheduledFutures();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {}, 1, MILLISECONDS);

    @DisplayName("예약 key 값과 수행될 scheduledFuture 을 받아 등록한다")
    @Test
    void register() {
        // when
        reservationScheduledFutures.register(RESERVATION_KEY, scheduledFuture);

        // then
        ScheduledFuture<?> actual = reservationScheduledFutures.findByKey(RESERVATION_KEY);
        assertThat((Future<?>) actual).isEqualTo(scheduledFuture);
    }

    @DisplayName("예약 key 값을 통해 예약된 수행을 취소한다")
    @Test
    void cancelScheduleByKey() {
        // given
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {}, 1, MILLISECONDS);
        reservationScheduledFutures.register(RESERVATION_KEY, scheduledFuture);

        // when
        boolean actual = reservationScheduledFutures.cancelScheduleByKey(RESERVATION_KEY);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("예약 key 값을 통해 수행값을 삭제한다")
    @Test
    void removeByKey() {
        // given
        reservationScheduledFutures.register(RESERVATION_KEY, scheduledFuture);
        reservationScheduledFutures.cancelScheduleByKey(RESERVATION_KEY);

        // when
        reservationScheduledFutures.removeByKey(RESERVATION_KEY);

        // then
        ScheduledFuture<?> actual = reservationScheduledFutures.findByKey(RESERVATION_KEY);
        assertThat((Future<?>) actual).isNull();
    }
}