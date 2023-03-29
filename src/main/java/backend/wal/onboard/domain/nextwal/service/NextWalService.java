package backend.wal.onboard.domain.nextwal.service;

import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.nextwal.NextWals;
import backend.wal.onboard.domain.nextwal.aggregate.Item;
import backend.wal.onboard.domain.nextwal.aggregate.NextWal;
import backend.wal.onboard.domain.nextwal.repository.ItemRepository;
import backend.wal.onboard.domain.nextwal.repository.NextWalRepository;
import backend.wal.support.annotation.DomainService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DomainService
public class NextWalService {

    private final ItemRepository itemRepository;
    private final NextWalRepository nextWalRepository;

    public NextWalService(final ItemRepository itemRepository, final NextWalRepository nextWalRepository) {
        this.itemRepository = itemRepository;
        this.nextWalRepository = nextWalRepository;
    }

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

    public NextWals getNextWalsByUserId(Long userId) {
        return new NextWals(nextWalRepository.findNextWalsByUserId(userId));
    }

    public void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType) {
        Long countOfCorrespondCategoryType = itemRepository.countAllByCategoryCategoryType(categoryType);
        double nextItemId = nextWals.calculateNextItemId(randomNextWal, countOfCorrespondCategoryType);
        Item nextItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(categoryType, nextItemId);
        randomNextWal.updateItemToNextItem(nextItem);
        nextWals.updateNextWalInfo(randomNextWal);
    }

    public void deleteNextWalsByCanceledCategoryTypes(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        nextWalRepository.deleteAllInBatch(
                nextWalRepository.findNextWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId));
    }
}
