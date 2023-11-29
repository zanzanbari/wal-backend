package backend.wal.wal.item.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.CountItemUseCase;
import backend.wal.wal.item.application.port.out.ItemPersistencePort;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class CountItemService implements CountItemUseCase {

    private final ItemPersistencePort itemPersistencePort;

    public CountItemService(ItemPersistencePort itemPersistencePort) {
        this.itemPersistencePort = itemPersistencePort;
    }

    @Override
    @Transactional
    public Long countAllCorrespondItemsByCategoryType(WalCategoryType categoryType) {
        return itemPersistencePort.countAllByCategoryCategoryType(categoryType);
    }
}
