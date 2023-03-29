package backend.wal.wal.todaywal.domain.service;

import backend.wal.wal.common.domain.aggregate.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeTypes;
import backend.wal.wal.common.domain.aggregate.WalTimeType;
import backend.wal.wal.todaywal.application.port.TodayWalSettingUseCase;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.support.annotation.DomainService;

import java.util.Set;

@DomainService
public class TodayWalSettingService implements TodayWalSettingUseCase {

    private final TodayWalRepository todayWalRepository;

    public TodayWalSettingService(final TodayWalRepository todayWalRepository) {
        this.todayWalRepository = todayWalRepository;
    }

    @Override
    public void registerTodayWal(Long userId, WalTimeType timeType, WalCategoryType categoryType, String content) {
        TodayWal todayWal = TodayWal.builder()
                .userId(userId)
                .timeType(timeType)
                .categoryType(categoryType)
                .message(content)
                .build();
        todayWalRepository.save(todayWal);
    }

    @Override
    public void deleteTodayWalsByWillCancelAfterNow(WalTimeTypes willCancelAfterNow, Long userId) {
        if (willCancelAfterNow.isExist()) {
            todayWalRepository.deleteAllInBatch(
                    todayWalRepository.findTodayWalsByTimeTypeInAndUserId(willCancelAfterNow.getValues(), userId));
        }
    }

    @Override
    public WalTimeTypes extractAfterNow(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        return WalTimeTypes.createCompareAfterNow(
                todayWalRepository.findTodayWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId));
    }
}
