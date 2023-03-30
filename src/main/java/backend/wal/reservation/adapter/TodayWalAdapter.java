package backend.wal.reservation.adapter;

import backend.wal.reservation.application.port.out.TodayWalPort;
import backend.wal.wal.todaywal.application.port.in.RegisterReservationTodayWalUseCase;
import org.springframework.stereotype.Component;

@Component
public final class TodayWalAdapter implements TodayWalPort {

    private final RegisterReservationTodayWalUseCase registerReservationTodayWalUseCase;

    public TodayWalAdapter(final RegisterReservationTodayWalUseCase registerReservationTodayWalUseCase) {
        this.registerReservationTodayWalUseCase = registerReservationTodayWalUseCase;
    }

    @Override
    public void registerReservationCall(Long userId, String message) {
        registerReservationTodayWalUseCase.registerReservationTodayWal(userId, message);
    }
}
