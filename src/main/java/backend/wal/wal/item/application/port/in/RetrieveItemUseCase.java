package backend.wal.wal.item.application.port.in;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Item;

public interface RetrieveItemUseCase {

    Item retrieveFirstByCategoryType(WalCategoryType categoryType);

    Item retrieveNextItemByCategoryTypeAndNextItemId(WalCategoryType categoryType, double nextItemId);
}
