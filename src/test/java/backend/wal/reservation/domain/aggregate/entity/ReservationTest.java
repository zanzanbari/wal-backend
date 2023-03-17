package backend.wal.reservation.domain.aggregate.entity;

import backend.wal.reservation.app.dto.AddReservationRequestDto;
import backend.wal.reservation.domain.aggregate.vo.ShowStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    private static final LocalDateTime STANDARD_DATE_TIME = LocalDateTime.parse("1998-04-02T13:30:00");

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        AddReservationRequestDto requestDto = AddReservationRequestDto.of(
                1L,
                "예약 메세지",
                "1998-04-02",
                "13:30:00",
                ShowStatus.OPEN
        );
        reservation = Reservation.newInstance(requestDto);
    }

    @DisplayName("날짜를 받아 예약된 날짜가 해당 날짜와 일치하면 true, 일치하지 않으면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideDateAndExpect")
    void isToday(CharSequence date, boolean expect) {
        // when
        boolean actual = reservation.isToday(LocalDate.parse(date));

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideDateAndExpect() {
        return Stream.of(
                Arguments.of("1998-04-01", false),
                Arguments.of("1998-04-02", true),
                Arguments.of("1998-04-03", false)
        );
    }

    @DisplayName("LocalDateTime 을 받아 Reservation 의 예약 시간과의 차이를 mills 로 반환한다")
    @ParameterizedTest
    @MethodSource("provideCompareDateTime")
    void pTest(LocalDateTime compareTime) {
        // given
        long expect = getDifferenceToMillisecond(compareTime);

        // when
        long actual = reservation.getDelayTimeAboutNow(compareTime);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideCompareDateTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.parse("1998-04-02T13:30:01")),
                Arguments.of(LocalDateTime.parse("1998-05-02T13:30:01")),
                Arguments.of(LocalDateTime.parse("1998-06-02T13:30:00")),
                Arguments.of(LocalDateTime.parse("1998-07-02T13:30:00"))
        );
    }

    private static long getDifferenceToMillisecond(LocalDateTime compareTime) {
        return Duration.between(compareTime, STANDARD_DATE_TIME)
                .toMillis();
    }
}