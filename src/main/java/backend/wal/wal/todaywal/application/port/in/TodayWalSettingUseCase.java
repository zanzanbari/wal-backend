package backend.wal.wal.todaywal.application.port.in;

import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.nextwal.domain.NextWals;

import java.util.Set;

public interface TodayWalSettingUseCase {

    void setTodayWals(Set<WalTimeType> timeTypes, Long userId, NextWals nextWals);
}
