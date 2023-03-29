package backend.wal.wal.common.adapter;

import backend.wal.wal.common.domain.WalTimeTypes;
import backend.wal.wal.common.domain.aggregate.WalCategoryType;
import backend.wal.wal.common.domain.aggregate.WalTimeType;
import backend.wal.wal.onboarding.adapter.WalSettingPort;
import backend.wal.wal.todaywal.application.port.TodayWalSettingUseCase;
import backend.wal.wal.nextwal.application.port.NextWalSettingUseCase;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;
import backend.wal.support.annotation.DomainService;

import java.util.Set;

@DomainService
public class WalSettingAdapter implements WalSettingPort {

    private final NextWalSettingUseCase nextWalSettingUseCase;
    private final TodayWalSettingUseCase todayWalSettingUseCase;

    public WalSettingAdapter(final NextWalSettingUseCase nextWalSettingUseCase,
                             final TodayWalSettingUseCase todayWalSettingUseCase) {
        this.nextWalSettingUseCase = nextWalSettingUseCase;
        this.todayWalSettingUseCase = todayWalSettingUseCase;
    }

    @Override
    public NextWals setNextWals(Set<WalCategoryType> categoryTypes, Long userId) {
        return nextWalSettingUseCase.setNextWals(categoryTypes, userId);
    }

    @Override
    public void setTodayWals(Set<WalTimeType> timeTypes, Long userId, NextWals nextWals) {
        for (WalTimeType timeType : timeTypes) {
            NextWal randomNextWal = nextWalSettingUseCase.getRandomNextWal(nextWals);
            WalCategoryType categoryType = randomNextWal.getCategoryType();
            todayWalSettingUseCase.registerTodayWal(userId, timeType, categoryType, randomNextWal.getItemContent());
            nextWalSettingUseCase.updateNextWal(nextWals, randomNextWal, categoryType);
        }
    }

    @Override
    public void updateTodayWalByAddTimeTypes(WalTimeTypes willAddAfterNow, Long userId) {
        setTodayWals(willAddAfterNow.getValues(), userId, nextWalSettingUseCase.getNextWalsByUserId(userId));
    }

    @Override
    public Set<WalTimeType> updateWalInfoByCancelCategoryTypes(Set<WalCategoryType> canceledCategoryTypes,
                                                               Long userId) {
        nextWalSettingUseCase.deleteNextWalsByCanceledCategoryTypes(canceledCategoryTypes, userId);
        WalTimeTypes willCancelAfterNow = todayWalSettingUseCase.extractAfterNow(canceledCategoryTypes, userId);
        todayWalSettingUseCase.deleteTodayWalsByWillCancelAfterNow(willCancelAfterNow, userId);
        return willCancelAfterNow.getValues();
    }

    @Override
    public void updateTodayWalByCancelTimeTypes(WalTimeTypes willCancelAfterNow, Long userId) {
        todayWalSettingUseCase.deleteTodayWalsByWillCancelAfterNow(willCancelAfterNow, userId);
    }

    @Override
    public void updateWalInfoByAddCategoryTypesInEmptyTimeTypes(Set<WalTimeType> emptiedTimeTypes,
                                                                Set<WalCategoryType> addedCategoryTypes,
                                                                Long userId) {
        setTodayWals(emptiedTimeTypes, userId, setNextWals(addedCategoryTypes, userId));
    }
}
