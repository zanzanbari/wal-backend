package backend.wal.wal.item.application.port.in;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Item;
import backend.wal.wal.item.domain.repository.FirstItemsResult;

import java.util.List;

public interface RetrieveItemUseCase {

    List<FirstItemsResult> retrieveFirstByCategoryType(Iterable<WalCategoryType> categoryTypes);

    Item retrieveNextItemByCategoryTypeAndNextItemId(WalCategoryType categoryType, double nextItemId);
}
