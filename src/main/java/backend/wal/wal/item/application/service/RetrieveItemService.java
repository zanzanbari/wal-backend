package backend.wal.wal.item.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.RetrieveItemUseCase;
import backend.wal.wal.item.domain.aggregate.Item;
import backend.wal.wal.item.domain.repository.ItemRepository;
import org.springframework.transaction.annotation.Transactional;

@AppService
@Transactional(readOnly = true)
public class RetrieveItemService implements RetrieveItemUseCase {

    private final ItemRepository itemRepository;

    public RetrieveItemService(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item retrieveFirstByCategoryType(WalCategoryType categoryType) {
        return itemRepository.findFirstByCategoryCategoryType(categoryType);
    }

    @Override
    public Item retrieveNextItemByCategoryTypeAndNextItemId(WalCategoryType categoryType, double nextItemId) {
        return itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(categoryType, nextItemId);
    }
}
