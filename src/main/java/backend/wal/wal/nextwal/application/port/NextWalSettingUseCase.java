package backend.wal.wal.nextwal.application.port;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;

import java.util.Set;

public interface NextWalSettingUseCase {

    NextWals setNextWals(Set<WalCategoryType> categoryTypes, Long userId);

    NextWal getRandomNextWal(NextWals nextWals);

    NextWals getNextWalsByUserId(Long userId);

    void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType);

    void deleteNextWalsByCanceledCategoryTypes(Set<WalCategoryType> canceledCategoryTypes, Long userId);
}
