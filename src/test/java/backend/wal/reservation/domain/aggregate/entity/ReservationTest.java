package backend.wal.reservation.domain.aggregate.entity;

import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.aggregate.ShowStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @DisplayName("날짜를 받아 예약된 날짜가 해당 날짜와 일치하면 true, 일치하지 않으면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideDateAndExpect")
    void isToday(CharSequence date, boolean expect) {
        // given
        AddReservationRequestDto requestDto = AddReservationRequestDto.of(
                1L,
                "예약 메세지",
                "1998-04-02",
                "13:30:00",
                ShowStatus.OPEN
        );
        Reservation reservation = Reservation.newInstance(requestDto);

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
}