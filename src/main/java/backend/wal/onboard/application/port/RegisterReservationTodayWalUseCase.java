package backend.wal.onboard.application.port;

public interface RegisterReservationTodayWalUseCase {

    void registerReservationTodayWal(Long userId, String message);
}
