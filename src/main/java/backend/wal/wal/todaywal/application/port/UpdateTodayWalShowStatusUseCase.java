package backend.wal.wal.todaywal.application.port;

import backend.wal.wal.todaywal.domain.aggregate.TodayWal;

import java.util.List;

public interface TodayWalUseCase {

    List<TodayWal> findTodayWalsByUserId(Long userId);

    void updateShowStatus(Long todayWalId, Long userId);
}
