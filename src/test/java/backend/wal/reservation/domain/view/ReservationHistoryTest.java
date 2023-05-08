package backend.wal.reservation.domain.view;

import backend.wal.reservation.application.port.in.dto.ReservationHistoryResponseDto;
import backend.wal.reservation.domain.aggregate.SendStatus;
import backend.wal.reservation.domain.aggregate.ShowStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReservationHistoryTest {

    @DisplayName("예약 메시지 조회 시 전송 시간, 예약한 시간, 조회 여부, 전송 여부, 디테일 메시지를 반환한다")
    @ParameterizedTest
    @MethodSource("provideSendTimeAndReserveTimeAndExpectDetailMessage")
    void getDetailSendDateInfo(CharSequence sendTimeText, CharSequence reserveTimeText, String expect) {
        // given
        final Long reservationId = 1L;
        final String message = "예약 메시지";
        final ShowStatus showStatus = ShowStatus.OPEN;
        final SendStatus sendStatus = SendStatus.NOT_DONE;

        ReservationHistory reservationHistory = new ReservationHistory(
                reservationId,
                message,
                LocalDateTime.parse(sendTimeText),
                showStatus,
                sendStatus,
                LocalDateTime.parse(reserveTimeText)
        );

        // when
        ReservationHistoryResponseDto detailSendDateInfo = reservationHistory.getDetailSendDateInfo();

        // then
        assertAll(
                () -> assertThat(detailSendDateInfo.getReservationId()).isEqualTo(reservationId),
                () -> assertThat(detailSendDateInfo.getMessage()).isEqualTo(message),
                () -> assertThat(detailSendDateInfo.getShowStatus()).isEqualTo(showStatus),
                () -> assertThat(detailSendDateInfo.getDetail()).isEqualTo(expect),
                () -> assertThat(detailSendDateInfo.getReservedAt()).isEqualTo("2011-12-30.00:00")
        );
    }

    private static Stream<Arguments> provideSendTimeAndReserveTimeAndExpectDetailMessage() {
        return Stream.of(
                Arguments.of("2011-12-31T11:59:59", "2011-12-30T00:00:00", "12.31 오전 11:59 토요일 • 전송 예정"),
                Arguments.of("2011-12-31T12:00:00", "2011-12-30T00:00:00", "12.31 오후 12:00 토요일 • 전송 예정"),
                Arguments.of("2011-12-31T00:00:00", "2011-12-30T00:00:00", "12.31 오전 0:00 토요일 • 전송 예정"),
                Arguments.of("2011-12-31T23:59:59", "2011-12-30T00:00:00", "12.31 오후 11:59 토요일 • 전송 예정")
        );
    }
}