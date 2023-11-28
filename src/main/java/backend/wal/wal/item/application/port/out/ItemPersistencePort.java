package backend.wal.wal.item.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.RegisterItemRequestDto;
import backend.wal.wal.item.domain.Item;

import java.util.List;

public interface ItemPersistencePort {

    void saveAll(List<RegisterItemRequestDto> requestDtos, WalCategoryType categoryType);

    List<Item> findFirstItemsByCategoryTypes(Iterable<WalCategoryType> categoryTypes);

    Long countAllByCategoryCategoryType(WalCategoryType categoryType);

    Item findByCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, int nextItemId);
}
