package backend.wal.wal.todaywal.domain.service;

import backend.wal.wal.todaywal.application.port.UpdateTodayWalShowStatusUseCase;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.wal.todaywal.exception.NotFoundTodayWalException;
import backend.wal.support.annotation.DomainService;
import org.springframework.transaction.annotation.Transactional;

@DomainService
public class UpdateTodayWalShowStatusService implements UpdateTodayWalShowStatusUseCase {

    private final TodayWalRepository todayWalRepository;

    public UpdateTodayWalShowStatusService(final TodayWalRepository todayWalRepository) {
        this.todayWalRepository = todayWalRepository;
    }

    @Override
    @Transactional
    public void updateShowStatus(Long todayWalId, Long userId) {
        TodayWal todayWal = todayWalRepository.findTodayWalByIdAndUserId(todayWalId, userId)
                .orElseThrow(() -> NotFoundTodayWalException.notExist(todayWalId, userId));
        todayWal.updateShowStatus();
    }
}
