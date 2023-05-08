package backend.wal.reservation.adapter;

import backend.wal.reservation.application.port.out.TodayWalPort;
import backend.wal.reservation.application.port.out.ReservationTodayWalRequestDto;
import backend.wal.wal.todaywal.application.port.in.ReservationTodayWalHandlerUseCase;

import org.springframework.stereotype.Component;

@Component
public final class TodayWalAdapter implements TodayWalPort {

    private final ReservationTodayWalHandlerUseCase reservationTodayWalHandlerUseCase;

    public TodayWalAdapter(final ReservationTodayWalHandlerUseCase reservationTodayWalHandlerUseCase) {
        this.reservationTodayWalHandlerUseCase = reservationTodayWalHandlerUseCase;
    }

    @Override
    public void registerReservationCall(ReservationTodayWalRequestDto requestDto) {
        reservationTodayWalHandlerUseCase.registerReservationTodayWal(
                requestDto.getUserId(),
                requestDto.getMessage(),
                requestDto.getSendTime()
        );
    }

    @Override
    public void deleteReservationCall(Long userId) {
        reservationTodayWalHandlerUseCase.deleteReservationTodayWal(userId);
    }
}
