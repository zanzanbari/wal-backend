package backend.wal.wal.item.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.CountItemUseCase;
import backend.wal.wal.item.domain.repository.ItemRepository;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class CountItemService implements CountItemUseCase {

    private final ItemRepository itemRepository;

    public CountItemService(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public Long countAllCorrespondItemsByCategoryType(WalCategoryType categoryType) {
        return itemRepository.countAllByCategoryCategoryType(categoryType);
    }
}
