package backend.wal.wal.nextwal.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.Item;
import backend.wal.wal.nextwal.domain.NextWal;

import java.util.Collection;
import java.util.List;

public interface NextWalPersistencePort {

    List<NextWal> saveAll(List<Item> items, Long userId);

    void updateNextWalItem(Long nextWalId, Long nextItemId);

    void deleteAllInBatchBy(Collection<WalCategoryType> canceledCategoryTypes, Long userId);

    List<NextWal> findNextWalsByUserId(Long userId);
}
