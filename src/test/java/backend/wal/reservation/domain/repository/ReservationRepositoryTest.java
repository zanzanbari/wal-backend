package backend.wal.reservation.domain.repository;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.reservation.application.port.dto.AddReservationRequestDto;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.aggregate.ShowStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
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
        AddReservationRequestDto requestDto = AddReservationRequestDto.of(USER_ID, "예약 메세지", "2019-12-31", "10:12:22", ShowStatus.OPEN);
        reservationRepository.save(Reservation.newInstance(requestDto));

        // when
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);
        boolean actual = reservationRepository.existsReservationBySendDueDateBetweenAndUserId(startDateTime, endDateTime, USER_ID);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideStartDateAndEndDateAndExpect() {
        return Stream.of(
                Arguments.of("2019-12-31T00:00", "2020-01-01T00:00", true),
                Arguments.of("2019-12-30T00:00", "2019-12-31T00:00", false)
        );
    }

    @DisplayName("유저의 아이디를 받아 해당 유저의 예약 정보를 가져온다")
    @Test
    void findReservationsByUserId() {
        // given
        setUpReservations();

        // when
        List<Reservation> reservations = reservationRepository.findReservationsByUserId(USER_ID);

        // then
        assertThat(reservations).hasSize(3);
    }

    @DisplayName("유저 아이디와 시간을 받아 해당 시간 이후의 예약 정보를 가져온다")
    @Test
    void findReservationsBySendDueDateAfterAndUserId() {
        // given
        setUpReservations();

        // when
        List<Reservation> reservations = reservationRepository.findReservationsBySendDueDateAfterAndUserId(LocalDateTime.parse("2020-01-01T00:00"), USER_ID);

        // then
        assertThat(reservations).hasSize(1);
    }

    private void setUpReservations() {
        AddReservationRequestDto first = AddReservationRequestDto.of(USER_ID, "예약메세지1", "2019-12-31", "23:59:59", ShowStatus.OPEN);
        AddReservationRequestDto second = AddReservationRequestDto.of(USER_ID, "예약메세지2", "2020-01-01", "00:00:00", ShowStatus.OPEN);
        AddReservationRequestDto third = AddReservationRequestDto.of(USER_ID, "예약메세지3", "2020-01-01", "00:00:01", ShowStatus.OPEN);
        reservationRepository.saveAll(List.of(Reservation.newInstance(first), Reservation.newInstance(second), Reservation.newInstance(third)));
    }
}