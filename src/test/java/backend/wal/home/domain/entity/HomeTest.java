package backend.wal.home.domain.entity;

import backend.wal.home.domain.Home;
import backend.wal.home.domain.OpenStatus;
import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.common.WalTimeType;
import backend.wal.reservation.domain.aggregate.ShowStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static backend.wal.home.domain.OpenStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

class HomeTest {

    @DisplayName("현재 시간을 받아 home 의 walTimeType 시간과 비교해 현재 시간 이후이면 OpenStatus 가 ABLE, 이전이면 UNABLE 을 반환한다")
    @ParameterizedTest
    @MethodSource("provideWalTimeTypeAndDateTimeTextAndExpect")
    void setOpenStatusBy(WalTimeType timeType, CharSequence dateTimeText, OpenStatus expect) {
        // given
        Home home = new Home(1L, timeType, WalCategoryType.COMEDY, "", ShowStatus.CLOSED);

        // when
        home.setOpenStatusBy(LocalDateTime.parse(dateTimeText));

        // then
        assertThat(home.getOpenStatus()).isEqualTo(expect);
    }

    private static Stream<Arguments> provideWalTimeTypeAndDateTimeTextAndExpect() {
        return Stream.of(
                Arguments.of(MORNING, "1998-04-02T07:59:59", UNABLE),
                Arguments.of(MORNING, "1998-04-02T08:00:01", ABLE),
                Arguments.of(AFTERNOON, "1998-04-02T13:59:59", UNABLE),
                Arguments.of(AFTERNOON, "1998-04-02T14:00:01", ABLE),
                Arguments.of(NIGHT, "1998-04-02T19:59:59", UNABLE),
                Arguments.of(NIGHT, "1998-04-02T20:00:01", ABLE)
        );
    }
}