package backend.wal.wal.todaywal.application.service;

import backend.wal.wal.todaywal.application.port.TodayWalUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.exception.NotFoundTodayWalException;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AppService
@Transactional(readOnly = true)
public class TodayWalService implements TodayWalUseCase {

    private final TodayWalRepository todayWalRepository;

    public TodayWalService(final TodayWalRepository todayWalRepository) {
        this.todayWalRepository = todayWalRepository;
    }

    @Override
    public List<TodayWal> findTodayWalsByUserId(Long userId) {
        return todayWalRepository.findTodayWalsByUserId(userId);
    }

    @Override
    public void updateShowStatus(Long todayWalId, Long userId) {
        TodayWal todayWal = todayWalRepository.findTodayWalByIdAndUserId(todayWalId, userId)
                .orElseThrow(() -> NotFoundTodayWalException.notExist(todayWalId, userId));
        todayWal.updateShowStatus();
    }
}
