package backend.wal.reservation.adapter;

import backend.wal.reservation.exception.NotFoundReservationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationScheduledFuturesTest {

    private static final Long RESERVATION_KEY = 1L;

    private final ReservationScheduledFutures reservationScheduledFutures = new ReservationScheduledFutures();

    @DisplayName("예약 key 값과 수행될 scheduledFuture 을 받아 등록한다")
    @Test
    void register() {
        // given
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {}, 1, MILLISECONDS);

        // when
        reservationScheduledFutures.register(RESERVATION_KEY, scheduledFuture);

        // then
        ScheduledFuture<?> actual = reservationScheduledFutures.findByKey(RESERVATION_KEY).get();
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
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {}, 1, MILLISECONDS);
        reservationScheduledFutures.register(RESERVATION_KEY, scheduledFuture);
        reservationScheduledFutures.cancelScheduleByKey(RESERVATION_KEY);

        // when
        reservationScheduledFutures.removeByKey(RESERVATION_KEY);

        // then
        Optional<ScheduledFuture<?>> actual = reservationScheduledFutures.findByKey(RESERVATION_KEY);
        assertThat(actual).isNotPresent();
    }


    @DisplayName("예약 key 이 없다면 에러가 발생한다")
    @Test
    void fail_cancelScheduleByKey() {
        // when, then
        assertThatThrownBy(() -> reservationScheduledFutures.cancelScheduleByKey(RESERVATION_KEY))
                .isInstanceOf(NotFoundReservationException.class)
                .hasMessage(String.format("존재하지 않거나 이미 삭제된 히스토리 (%s) 입니다", RESERVATION_KEY));
    }
}