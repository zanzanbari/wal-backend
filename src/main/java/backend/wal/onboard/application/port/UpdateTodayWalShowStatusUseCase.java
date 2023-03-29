package backend.wal.onboard.application.port;

public interface UpdateTodayWalShowStatusUseCase {

    void updateShowStatus(Long todayWalId, Long userId);
}
