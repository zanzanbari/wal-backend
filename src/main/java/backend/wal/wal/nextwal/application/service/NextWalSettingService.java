package backend.wal.wal.nextwal.application.service;

import backend.wal.wal.nextwal.application.port.NextWalSettingUseCase;
import backend.wal.wal.nextwal.domain.repository.NextWalRepository;
import backend.wal.wal.nextwal.domain.repository.ItemRepository;
import backend.wal.wal.nextwal.domain.support.RandomRangeGenerator;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.Item;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.support.annotation.DomainService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DomainService
public class NextWalSettingService implements NextWalSettingUseCase {

    private final ItemRepository itemRepository;
    private final NextWalRepository nextWalRepository;

    public NextWalSettingService(final ItemRepository itemRepository, final NextWalRepository nextWalRepository) {
        this.itemRepository = itemRepository;
        this.nextWalRepository = nextWalRepository;
    }

    @Override
    public NextWals setNextWals(Set<WalCategoryType> categoryTypes, Long userId) {
        List<NextWal> nextWals = categoryTypes.stream()
                .map(categoryType -> {
                    Item firstItem = itemRepository.findFirstByCategoryCategoryType(categoryType);
                    NextWal nextWal = NextWal.newInstance(userId, categoryType, firstItem);
                    return nextWalRepository.save(nextWal);
                })
                .collect(Collectors.toList());
        return new NextWals(nextWals);
    }

    @Override
    public void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType) {
        Long countOfCorrespondCategoryType = itemRepository.countAllByCategoryCategoryType(categoryType);
        double nextItemId = nextWals.calculateNextItemId(randomNextWal, countOfCorrespondCategoryType);
        Item nextItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(categoryType, nextItemId);
        randomNextWal.updateItemToNextItem(nextItem);
        nextWals.updateNextWalInfo(randomNextWal);
    }

    @Override
    public void deleteNextWalsByCanceledCategoryTypes(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        nextWalRepository.deleteAllInBatch(
                nextWalRepository.findNextWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId));
    }
}
