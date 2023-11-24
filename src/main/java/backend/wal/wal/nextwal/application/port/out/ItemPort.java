package backend.wal.wal.nextwal.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Item;

import java.util.List;

public interface ItemPort {

    List<Item> retrieveFirstByCategoryType(Iterable<WalCategoryType> categoryTypes);

    Item retrieveNextItemByCategoryTypeAndNextItemId(WalCategoryType categoryType, double nextItemId);

    Long countAllCorrespondItemsByCategoryType(WalCategoryType categoryType);
}
