package backend.wal.wal.todaywal.application.port.in;

public interface ReservationTodayWalHandlerUseCase {

    void registerReservationTodayWal(Long userId, String message);

    void deleteReservationTodayWal(Long userId);
}
