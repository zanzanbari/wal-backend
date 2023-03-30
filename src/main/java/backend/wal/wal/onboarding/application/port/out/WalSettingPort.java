package backend.wal.wal.onboarding.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.nextwal.domain.NextWals;

import java.util.Set;

public interface WalSettingPort {

    NextWals setNextWals(Set<WalCategoryType> categoryTypes, Long userId);

    void setTodayWals(Set<WalTimeType> timeTypes, Long userId, NextWals nextWals);

    void updateTodayWalByAddTimeTypes(Set<WalTimeType> willAddAfterNow, Long userId);

    void updateTodayWalByCancelTimeTypes(Set<WalTimeType> willCancelAfterNow, Long userId);

    Set<WalTimeType> updateWalInfoByCancelCategoryTypes(Set<WalCategoryType> canceledCategoryTypes, Long userId);

    void updateWalInfoByAddCategoryTypesInEmptyTimeTypes(Set<WalTimeType> emptiedTimeTypes,
                                                         Set<WalCategoryType> addedCategoryTypes,
                                                         Long userId);
}
