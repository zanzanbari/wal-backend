package backend.wal.wal.todaywal.application.port.in;

import java.time.LocalDateTime;

public interface ReservationTodayWalHandlerUseCase {

    void registerReservationTodayWal(Long userId, String message, LocalDateTime sendTime);

    void deleteReservationTodayWal(Long userId);
}
