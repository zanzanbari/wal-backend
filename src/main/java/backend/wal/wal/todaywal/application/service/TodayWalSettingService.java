package backend.wal.wal.todaywal.application.service;

import backend.wal.wal.todaywal.application.port.TodayWalSettingUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.WalTimeTypes;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.support.annotation.AppService;

import java.util.Set;

@AppService
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
    public void deleteTodayWalsByWillCancelAfterNow(Set<WalTimeType> willCancelAfterNow, Long userId) {
        if (!willCancelAfterNow.isEmpty()) {
            todayWalRepository.deleteAllInBatch(
                    todayWalRepository.findTodayWalsByTimeTypeInAndUserId(willCancelAfterNow, userId));
        }
    }

    @Override
    public Set<WalTimeType> extractAfterNow(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        return WalTimeTypes.createCompareAfterNow(
                todayWalRepository.findTodayWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId)
        ).getValues();
    }
}
