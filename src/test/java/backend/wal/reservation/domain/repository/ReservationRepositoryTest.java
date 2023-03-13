package backend.wal.reservation.domain.repository;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.reservation.app.dto.AddReservationRequestDto;
import backend.wal.reservation.domain.aggregate.entity.Reservation;
import backend.wal.reservation.domain.aggregate.vo.ShowStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@JpaRepositoryTestConfig
class ReservationRepositoryTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("유저아이디, 시작 날짜와 끝 날짜를 받아 두 날짜 사이에 예약한 정보가 있으면 true, 없으면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideStartDateAndEndDateAndExpect")
    void existsReservationBySendDueDateBetweenAndUserId(CharSequence startDate,
                                                        CharSequence endDate,
                                                        boolean expect) {
        // given
        AddReservationRequestDto requestDto = AddReservationRequestDto.of(
                USER_ID,
                "예약 메세지",
                "2019-12-31",
                "10:12:22",
                ShowStatus.OPEN
        );
        reservationRepository.save(Reservation.newInstance(requestDto));

        // when
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);
        boolean actual = reservationRepository
                .existsReservationBySendDueDateBetweenAndUserId(startDateTime, endDateTime, USER_ID);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideStartDateAndEndDateAndExpect() {
        return Stream.of(
                Arguments.of("2019-12-31T00:00", "2020-01-01T00:00", true),
                Arguments.of("2019-12-30T00:00", "2019-12-31T00:00", false)
        );
    }
}