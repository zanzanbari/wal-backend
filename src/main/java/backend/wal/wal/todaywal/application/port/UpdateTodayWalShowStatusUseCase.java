package backend.wal.wal.todaywal.application.port;

public interface UpdateTodayWalShowStatusUseCase {

    void updateShowStatus(Long todayWalId, Long userId);
}
