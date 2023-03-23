package backend.wal.user.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ResignUserSchedulerTest {

    private final ResignUserScheduler resignUserScheduler = new ResignUserScheduler();

    @AfterEach
    void cleanUp() {
        resignUserScheduler.shoutDown();
    }

    @DisplayName("딜레이 시간(millis)과 수행해야 할 task 를 받아 딜레이 시간 이후 task 를 실행한다")
    @Test
    void resignAfterDay() {
        // given
        CountDownLatch latch = new CountDownLatch(1);
        long delayTimeMillis = 100;
        boolean[] taskChecker = { false };
        Runnable task = () -> {
            taskChecker[0] = true;
            latch.countDown();
        };

        // when
        resignUserScheduler.resignAfterDay(delayTimeMillis, task);

        // then
        assertAll(
                () -> assertThat(latch.await(delayTimeMillis, TimeUnit.MILLISECONDS)).isTrue(),
                () -> assertThat(taskChecker[0]).isTrue()
        );
    }
}