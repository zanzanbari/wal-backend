package backend.wal.wal.todaywal.application.port;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.nextwal.domain.NextWals;

import java.util.Set;

public interface TodayWalSettingUseCase {

    void setTodayWals(Set<WalTimeType> timeTypes, Long userId, NextWals nextWals);

    void registerTodayWal(Long userId, WalTimeType timeType, WalCategoryType categoryType, String content);

    void deleteTodayWalsByWillCancelAfterNow(Set<WalTimeType> willCancelAfterNow, Long userId);

    Set<WalTimeType> extractAfterNow(Set<WalCategoryType> canceledCategoryTypes, Long userId);
}
