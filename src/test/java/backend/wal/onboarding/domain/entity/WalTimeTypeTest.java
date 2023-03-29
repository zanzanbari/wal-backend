package backend.wal.onboarding.domain.entity;

import backend.wal.onboard.domain.common.WalTimeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class WalTimeTypeTest {

    @DisplayName("WalTimeType 의 정해진 시간을 받아온 시간과 비교해 이후이면 해당 true 를 반환하고, 이전이면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideWalTimeTypeAndDateTimeTextAndIsAfterNowExpect")
    void isAfterNow(WalTimeType timeType, CharSequence dateTimeText, boolean expect) {
        // when
        boolean actual = timeType.isAfterNow(LocalDateTime.parse(dateTimeText));

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideWalTimeTypeAndDateTimeTextAndIsAfterNowExpect() {
        return Stream.of(
                Arguments.of(MORNING, "1998-04-02T07:59:59", true),
                Arguments.of(MORNING, "1998-04-02T08:00:01", false),
                Arguments.of(AFTERNOON, "1998-04-02T13:59:59", true),
                Arguments.of(AFTERNOON, "1998-04-02T14:00:01", false),
                Arguments.of(NIGHT, "1998-04-02T19:59:59", true),
                Arguments.of(NIGHT, "1998-04-02T20:00:01", false)
        );
    }

    @DisplayName("WalTimeType 의 정해진 시간을 받아온 시간과 비교해 이후이면 해당 WalTimeType 을 반환하고, 이전이면 null 을 반환한다")
    @ParameterizedTest
    @MethodSource("provideWalTimeTypeAndDateTimeTextAndExpect")
    void findAfterNow(WalTimeType timeType, CharSequence dateTimeText, WalTimeType expect) {
        // when
        WalTimeType timeTypeAfterNow = timeType.findAfterNow(LocalDateTime.parse(dateTimeText));

        // then
        assertThat(timeTypeAfterNow).isEqualTo(expect);
    }

    private static Stream<Arguments> provideWalTimeTypeAndDateTimeTextAndExpect() {
        return Stream.of(
                Arguments.of(MORNING, "1998-04-02T07:59:59", MORNING),
                Arguments.of(MORNING, "1998-04-02T08:00:01", null),
                Arguments.of(AFTERNOON, "1998-04-02T13:59:59", AFTERNOON),
                Arguments.of(AFTERNOON, "1998-04-02T14:00:01", null),
                Arguments.of(NIGHT, "1998-04-02T19:59:59", NIGHT),
                Arguments.of(NIGHT, "1998-04-02T20:00:01", null)
        );
    }
}