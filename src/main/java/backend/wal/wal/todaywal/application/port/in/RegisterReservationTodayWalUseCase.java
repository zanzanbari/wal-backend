package backend.wal.wal.todaywal.application.port.in;

public interface RegisterReservationTodayWalUseCase {

    void registerReservationTodayWal(Long userId, String message);
}
