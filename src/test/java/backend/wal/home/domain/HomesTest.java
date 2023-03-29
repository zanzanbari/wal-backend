package backend.wal.home.domain;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.reservation.domain.aggregate.ShowStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static backend.wal.home.domain.OpenStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

class HomesTest {

    @DisplayName(
            "Home 정보들과 현재 시간을 받아 create 하면 현재 시간과 각각 Home 의 walTimeType 시간을 비교해" +
            "walTimeType 이 현재시간 이후이면 OpenStatus 가 ABLE 이고, 현재시간 이전이면 UNABLE 이 된다")
    @ParameterizedTest
    @MethodSource("provideWalTimeTypeAndDateTimeTextAndExpect")
    void create(WalTimeType timeType, CharSequence dateTimeText, List<OpenStatus> expect) {
        // given
        Home home = new Home(1L, timeType, WalCategoryType.COMEDY, "", ShowStatus.CLOSED);
        List<Home> homeInfo = List.of(home);

        // when
        Homes homes = Homes.create(homeInfo, LocalDateTime.parse(dateTimeText));

        // then
        List<OpenStatus> actual = homes.getEachOpenStatus();
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideWalTimeTypeAndDateTimeTextAndExpect() {
        return Stream.of(
                Arguments.of(MORNING, "1998-04-02T07:59:59", List.of(UNABLE)),
                Arguments.of(MORNING, "1998-04-02T08:00:01", List.of(ABLE)),
                Arguments.of(AFTERNOON, "1998-04-02T13:59:59", List.of(UNABLE)),
                Arguments.of(AFTERNOON, "1998-04-02T14:00:01", List.of(ABLE)),
                Arguments.of(NIGHT, "1998-04-02T19:59:59", List.of(UNABLE)),
                Arguments.of(NIGHT, "1998-04-02T20:00:01", List.of(ABLE))
        );
    }
}