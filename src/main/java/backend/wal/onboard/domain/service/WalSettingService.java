package backend.wal.onboard.domain.service;

import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.common.WalTimeTypes;
import backend.wal.onboard.domain.common.WalTimeType;
import backend.wal.onboard.domain.nextwal.NextWals;
import backend.wal.onboard.domain.nextwal.support.RandomRangeGenerator;
import backend.wal.onboard.domain.nextwal.aggregate.NextWal;
import backend.wal.onboard.domain.nextwal.service.NextWalService;
import backend.wal.onboard.domain.todaywal.aggregate.TodayWal;
import backend.wal.onboard.domain.todaywal.service.TodayWalSettingService;
import backend.wal.support.annotation.DomainService;

import java.util.Set;

@DomainService
public class WalSettingService {

    private final NextWalService nextWalService;
    private final TodayWalSettingService todayWalSettingService;
    private final RandomRangeGenerator randomRangeGenerator;

    public WalSettingService(final NextWalService nextWalService, final TodayWalSettingService todayWalSettingService,
                             final RandomRangeGenerator randomRangeGenerator) {
        this.nextWalService = nextWalService;
        this.todayWalSettingService = todayWalSettingService;
        this.randomRangeGenerator = randomRangeGenerator;
    }

    public NextWals setNextWals(Set<WalCategoryType> categoryTypes, Long userId) {
        return nextWalService.setNextWals(categoryTypes, userId);
    }

    public void setTodayWals(Set<WalTimeType> timeTypes, Long userId, NextWals nextWals) {
        for (WalTimeType timeType : timeTypes) {
            NextWal randomNextWal = nextWals.getRandomBy(randomRangeGenerator);
            WalCategoryType categoryType = randomNextWal.getCategoryType();
            TodayWal todayWal = TodayWal.builder()
                    .userId(userId)
                    .timeType(timeType)
                    .categoryType(categoryType)
                    .message(randomNextWal.getItemContent())
                    .build();
            todayWalSettingService.registerTodayWal(todayWal);
            nextWalService.updateNextWal(nextWals, randomNextWal, categoryType);
        }
    }

    public void updateTodayWalByAddTimeTypes(WalTimeTypes willAddAfterNow, Long userId) {
        setTodayWals(willAddAfterNow.getValues(), userId, nextWalService.getNextWalsByUserId(userId));
    }

    public Set<WalTimeType> updateWalInfoByCancelCategoryTypes(Set<WalCategoryType> canceledCategoryTypes,
                                                               Long userId) {
        nextWalService.deleteNextWalsByCanceledCategoryTypes(canceledCategoryTypes, userId);
        WalTimeTypes willCancelAfterNow = todayWalSettingService.extractAfterNow(canceledCategoryTypes, userId);
        todayWalSettingService.deleteTodayWalsByWillCancelAfterNow(willCancelAfterNow, userId);
        return willCancelAfterNow.getValues();
    }

    public void updateTodayWalByCancelTimeTypes(WalTimeTypes willCancelAfterNow, Long userId) {
        todayWalSettingService.deleteTodayWalsByWillCancelAfterNow(willCancelAfterNow, userId);
    }

    public void updateWalInfoByAddCategoryTypesInEmptyTimeTypes(Set<WalTimeType> emptiedTimeTypes,
                                                                Set<WalCategoryType> addedCategoryTypes,
                                                                Long userId) {
        setTodayWals(emptiedTimeTypes, userId, setNextWals(addedCategoryTypes, userId));
    }
}
