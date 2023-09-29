package backend.wal.wal.nextwal.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Item;

public interface ItemPort {
    Item retrieveFirstByCategoryType(WalCategoryType categoryType);

    Item retrieveNextItemByCategoryTypeAndNextItemId(WalCategoryType categoryType, double nextItemId);

    Long countAllCorrespondItemsByCategoryType(WalCategoryType categoryType);
}
