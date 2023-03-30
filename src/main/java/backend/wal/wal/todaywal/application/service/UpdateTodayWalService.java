package backend.wal.wal.todaywal.application.service;

import backend.wal.wal.todaywal.application.port.in.UpdateTodayWalUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.WalTimeTypes;
import backend.wal.wal.todaywal.exception.NotFoundTodayWalException;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AppService
@Transactional
public class UpdateTodayWalService implements UpdateTodayWalUseCase {

    private final TodayWalRepository todayWalRepository;

    public UpdateTodayWalService(final TodayWalRepository todayWalRepository) {
        this.todayWalRepository = todayWalRepository;
    }

    @Override
    public void updateShowStatus(Long todayWalId, Long userId) {
        TodayWal todayWal = todayWalRepository.findTodayWalByIdAndUserId(todayWalId, userId)
                .orElseThrow(() -> NotFoundTodayWalException.notExist(todayWalId, userId));
        todayWal.updateShowStatus();
    }

    @Override
    public Set<WalTimeType> updateTodayWalByCanceledCategoryTypesAndUserId(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        Set<WalTimeType> willCancelAfterNow = extractAfterNow(canceledCategoryTypes, userId);
        deleteTodayWalsByWillCancelAfterNow(willCancelAfterNow, userId);
        return willCancelAfterNow;
    }

    private Set<WalTimeType> extractAfterNow(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        return WalTimeTypes.createCompareAfterNow(
                todayWalRepository.findTodayWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId)
        ).getValues();
    }

    @Override
    public void deleteTodayWalsByWillCancelAfterNow(Set<WalTimeType> willCancelAfterNow, Long userId) {
        if (!willCancelAfterNow.isEmpty()) {
            todayWalRepository.deleteAllInBatch(
                    todayWalRepository.findTodayWalsByTimeTypeInAndUserId(willCancelAfterNow, userId));
        }
    }
}
