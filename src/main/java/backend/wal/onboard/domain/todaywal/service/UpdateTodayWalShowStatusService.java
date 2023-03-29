package backend.wal.onboard.domain.todaywal.service;

import backend.wal.onboard.application.port.UpdateTodayWalShowStatusUseCase;
import backend.wal.onboard.domain.todaywal.aggregate.TodayWal;
import backend.wal.onboard.domain.todaywal.repository.TodayWalRepository;
import backend.wal.onboard.exception.NotFoundTodayWalException;
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
