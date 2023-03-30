package backend.wal.wal.onboarding.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;

import java.util.Set;

public interface WalSettingPort {

    void setWalInfo(Set<WalTimeType> timeTypes, Set<WalCategoryType> categoryTypes, Long userId);

    void updateTodayWalByAddTimeTypes(Set<WalTimeType> willAddAfterNow, Long userId);

    void updateTodayWalByCancelTimeTypes(Set<WalTimeType> willCancelAfterNow, Long userId);

    Set<WalTimeType> updateWalInfoByCancelCategoryTypes(Set<WalCategoryType> canceledCategoryTypes, Long userId);

    void updateWalInfoByAddCategoryTypesInEmptyTimeTypes(Set<WalTimeType> emptiedTimeTypes,
                                                    Set<WalCategoryType> addedCategoryTypes,
                                                    Long userId);
}
