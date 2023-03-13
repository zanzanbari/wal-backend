package backend.wal.reservation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTimeTest {

    @DisplayName("예약 날짜를 받아 예약 날짜의 시작 시간을 가져온다")
    @ParameterizedTest
    @MethodSource("provideDateAndExpectDate")
    void getReservationDate(CharSequence date, String dateTimeAsString) {
        // given
        ReservationTime reservationTime = ReservationTime.startDayFrom(LocalDate.parse(date));

        // when
        LocalDateTime reservationDate = reservationTime.getReservationDate();

        // then
        assertThat(reservationDate).isEqualTo(dateTimeAsString);
    }

    private static Stream<Arguments> provideDateAndExpectDate() {
        return Stream.of(
                Arguments.of("1998-04-02", "1998-04-02T00:00"),
                Arguments.of("2012-12-31", "2012-12-31T00:00"),
                Arguments.of("2023-03-11", "2023-03-11T00:00")
        );
    }

    @DisplayName("예약 날짜를 받아 예약 날짜의 다음날 시작 시간을 가져온다")
    @ParameterizedTest
    @MethodSource("provideDateAndExpectNextDate")
    void getNextDate(CharSequence date, String dateTimeAsString) {
        // given
        ReservationTime reservationTime = ReservationTime.startDayFrom(LocalDate.parse(date));

        // when
        LocalDateTime nextDate = reservationTime.getNextDate();

        // then
        assertThat(nextDate).isEqualTo(dateTimeAsString);
    }

    private static Stream<Arguments> provideDateAndExpectNextDate() {
        return Stream.of(
                Arguments.of("1998-04-02", "1998-04-03T00:00"),
                Arguments.of("2012-12-31", "2013-01-01T00:00"),
                Arguments.of("2023-03-11", "2023-03-12T00:00")
        );
    }
}