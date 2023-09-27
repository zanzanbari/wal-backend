package backend.wal.wal.onboarding.adapter;

import backend.wal.wal.onboarding.application.port.out.WalSettingPort;
import backend.wal.wal.todaywal.application.port.in.TodayWalSettingUseCase;
import backend.wal.wal.todaywal.application.port.in.UpdateTodayWalUseCase;
import backend.wal.wal.nextwal.application.port.in.GetNextWalUseCase;
import backend.wal.wal.nextwal.application.port.in.NextWalSettingUseCase;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public final class WalSettingAdapter implements WalSettingPort {

    private final TodayWalSettingUseCase todayWalSettingUseCase;
    private final UpdateTodayWalUseCase updateTodayWalUseCase;
    private final NextWalSettingUseCase nextWalSettingUseCase;
    private final GetNextWalUseCase getNextWalUseCase;

    public WalSettingAdapter(final TodayWalSettingUseCase todayWalSettingUseCase,
                             final UpdateTodayWalUseCase updateTodayWalUseCase,
                             final NextWalSettingUseCase nextWalSettingUseCase,
                             final GetNextWalUseCase getNextWalUseCase) {
        this.todayWalSettingUseCase = todayWalSettingUseCase;
        this.updateTodayWalUseCase = updateTodayWalUseCase;
        this.nextWalSettingUseCase = nextWalSettingUseCase;
        this.getNextWalUseCase = getNextWalUseCase;
    }

    @Override
    public void setWalInfo(Set<WalTimeType> timeTypes, Set<WalCategoryType> categoryTypes, Long userId) {
        todayWalSettingUseCase.setTodayWals(timeTypes, userId, nextWalSettingUseCase.setNextWals(categoryTypes, userId));
    }

    @Override
    public void updateTodayWalByAddTimeTypes(Set<WalTimeType> willAddAfterNow, Long userId) {
        todayWalSettingUseCase.setTodayWals(willAddAfterNow, userId, getNextWalUseCase.getNextWalsByUserId(userId));
    }

    @Override
    public void updateTodayWalByCancelTimeTypes(Set<WalTimeType> willCancelAfterNow, Long userId) {
        updateTodayWalUseCase.deleteTodayWalsByWillCancelAfterNow(willCancelAfterNow, userId);
    }

    @Override
    public Set<WalTimeType> updateWalInfoByCancelCategoryTypes(Set<WalCategoryType> canceledCategoryTypes,
                                                               Long userId) {
        nextWalSettingUseCase.deleteNextWalsByCanceledCategoryTypes(canceledCategoryTypes, userId);
        return updateTodayWalUseCase.updateTodayWalByCanceledCategoryTypesAndUserId(canceledCategoryTypes, userId);
    }

    @Override
    public void updateWalInfoByAddCategoryTypesInEmptyTimeTypes(Set<WalTimeType> emptiedTimeTypes,
                                                                Set<WalCategoryType> addedCategoryTypes,
                                                                Long userId) {
        todayWalSettingUseCase.setTodayWals(emptiedTimeTypes, userId,
                nextWalSettingUseCase.setNextWals(addedCategoryTypes, userId));
    }
}
