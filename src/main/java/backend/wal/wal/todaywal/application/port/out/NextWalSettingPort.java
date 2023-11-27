package backend.wal.wal.todaywal.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.NextWal;

public interface NextWalSettingPort {

    NextWal getRandomNextWal(NextWals nextWals);

    void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType);
}
