package backend.wal.onboard.domain.todaywal.service;

import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.common.WalTimeTypes;
import backend.wal.onboard.domain.todaywal.aggregate.TodayWal;
import backend.wal.onboard.domain.todaywal.repository.TodayWalRepository;
import backend.wal.support.annotation.DomainService;

import java.util.Set;

@DomainService
public class TodayWalSettingService {

    private final TodayWalRepository todayWalRepository;

    public TodayWalSettingService(final TodayWalRepository todayWalRepository) {
        this.todayWalRepository = todayWalRepository;
    }

    public void registerTodayWal(TodayWal todayWal) {
        todayWalRepository.save(todayWal);
    }

    public void deleteTodayWalsByWillCancelAfterNow(WalTimeTypes willCancelAfterNow, Long userId) {
        if (willCancelAfterNow.isExist()) {
            todayWalRepository.deleteAllInBatch(
                    todayWalRepository.findTodayWalsByTimeTypeInAndUserId(willCancelAfterNow.getValues(), userId));
        }
    }

    public WalTimeTypes extractAfterNow(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        return WalTimeTypes.createCompareAfterNow(
                todayWalRepository.findTodayWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId));
    }
}
