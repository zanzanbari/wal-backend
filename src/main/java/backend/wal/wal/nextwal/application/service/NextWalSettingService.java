package backend.wal.wal.nextwal.application.service;

import backend.wal.support.annotation.DomainService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.out.ItemPersistencePort;
import backend.wal.wal.item.domain.Item;
import backend.wal.wal.nextwal.application.port.in.NextWalSettingUseCase;
import backend.wal.wal.nextwal.application.port.out.NextWalPersistencePort;
import backend.wal.wal.nextwal.domain.NextWal;
import backend.wal.wal.nextwal.domain.NextWals;

import java.util.List;
import java.util.Set;

@DomainService
public class NextWalSettingService implements NextWalSettingUseCase {

    private final NextWalPersistencePort nextWalPersistencePort;
    private final ItemPersistencePort itemPersistencePort;

    public NextWalSettingService(NextWalPersistencePort nextWalPersistencePort, ItemPersistencePort itemPersistencePort) {
        this.nextWalPersistencePort = nextWalPersistencePort;
        this.itemPersistencePort = itemPersistencePort;
    }

    @Override
    public NextWals setNextWals(Set<WalCategoryType> categoryTypes, Long userId) {
        List<Item> items = itemPersistencePort.findFirstItemsByCategoryTypes(categoryTypes);
        List<NextWal> nextWals = nextWalPersistencePort.saveAll(items, userId);
        return new NextWals(nextWals);
    }

    @Override
    public void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType) {
        Long countOfCorrespondCategoryType = itemPersistencePort.countAllByCategoryCategoryType(categoryType);
        int nextItemId = nextWals.calculateNextItemId(randomNextWal, countOfCorrespondCategoryType);
        Item nextItem = itemPersistencePort.findByCategoryTypeAndCategoryItemNumber(categoryType, nextItemId);
        nextWalPersistencePort.updateNextWalItem(randomNextWal.getId(), nextItem.getId());
        randomNextWal.updateItem(nextItem);
        nextWals.updateNextWalInfo(randomNextWal);
    }

    @Override
    public void deleteNextWalsByCanceledCategoryTypes(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        nextWalPersistencePort.deleteAllInBatchBy(canceledCategoryTypes, userId);
    }
}
