package backend.wal.reservation.domain;

import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;
import backend.wal.reservation.application.port.in.dto.ReservationCalendarResponseDto;
import backend.wal.reservation.application.port.in.dto.ReservationHistoryResponseDto;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.aggregate.ShowStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationsTest {

    private static final Long USER_ID = 1L;

    private Reservation firstReservation;
    private Reservations reservations;

    @BeforeEach
    void setUp() {
        AddReservationRequestDto first = AddReservationRequestDto.of(USER_ID,
                "예약메세지1",
                "2019-12-31",
                "23:59:59",
                ShowStatus.OPEN);
        AddReservationRequestDto second = AddReservationRequestDto.of(USER_ID,
                "예약메세지2",
                "2020-01-01",
                "00:00:00",
                ShowStatus.OPEN);
        AddReservationRequestDto third = AddReservationRequestDto.of(USER_ID,
                "예약메세지3",
                "2020-01-01",
                "00:00:01",
                ShowStatus.OPEN);

        firstReservation = Reservation.newInstance(first);
        Reservation secondReservation = Reservation.newInstance(second);
        Reservation thirdReservation = Reservation.newInstance(third);

        reservations = new Reservations(List.of(firstReservation, secondReservation, thirdReservation));
    }

    @DisplayName("Reservations 에서 전송 에정인 예약들을 가져온다")
    @Test
    void extractNotDoneReservation() {
        // given
        firstReservation.finish();

        // when
        List<ReservationHistoryResponseDto> responseDtos = reservations.extractNotDoneReservation();

        // then
        assertThat(responseDtos).hasSize(2);
    }

    @DisplayName("Reservations 에서 전송이 완료된 예약들을 가져온다")
    @Test
    void extractDoneReservation() {
        // given
        firstReservation.finish();

        // when
        List<ReservationHistoryResponseDto> responseDtos = reservations.extractDoneReservation();

        // then
        assertThat(responseDtos).hasSize(1);
    }

    @DisplayName("Reservations 에서 각각의 Reservation 의 시간 정보를 가져온다")
    @Test
    void extractDateForCalender() {
        // when
        List<ReservationCalendarResponseDto> responseDtos = reservations.extractDateForCalender();

        // then
        assertThat(responseDtos).hasSize(3);
    }
}