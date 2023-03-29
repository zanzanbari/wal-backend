package backend.wal.wal.nextwal.domain.service;

import backend.wal.wal.nextwal.application.port.NextWalSettingUseCase;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.Item;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;
import backend.wal.wal.nextwal.domain.repository.ItemRepository;
import backend.wal.wal.nextwal.domain.repository.NextWalRepository;
import backend.wal.wal.nextwal.domain.support.RandomRangeGenerator;
import backend.wal.wal.common.domain.aggregate.WalCategoryType;
import backend.wal.support.annotation.DomainService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DomainService
public class NextWalService implements NextWalSettingUseCase {

    private final ItemRepository itemRepository;
    private final NextWalRepository nextWalRepository;
    private final RandomRangeGenerator randomRangeGenerator;

    public NextWalService(final ItemRepository itemRepository, final NextWalRepository nextWalRepository,
                          final RandomRangeGenerator randomRangeGenerator) {
        this.itemRepository = itemRepository;
        this.nextWalRepository = nextWalRepository;
        this.randomRangeGenerator = randomRangeGenerator;
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

    public NextWal getRandomNextWal(NextWals nextWals) {
        return nextWals.getRandomBy(randomRangeGenerator);
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
