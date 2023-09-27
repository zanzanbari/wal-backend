package backend.wal.wal.nextwal.application.service;

import backend.wal.support.annotation.DomainService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Item;
import backend.wal.wal.nextwal.application.port.NextWalSettingUseCase;
import backend.wal.wal.nextwal.application.port.out.ItemPort;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;
import backend.wal.wal.nextwal.domain.repository.NextWalRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DomainService
public class NextWalSettingService implements NextWalSettingUseCase {

    private final NextWalRepository nextWalRepository;
    private final ItemPort itemPort;

    public NextWalSettingService(final NextWalRepository nextWalRepository, final ItemPort itemPort) {
        this.nextWalRepository = nextWalRepository;
        this.itemPort = itemPort;
    }

    @Override
    public NextWals setNextWals(Set<WalCategoryType> categoryTypes, Long userId) {
        List<NextWal> nextWals = categoryTypes.stream()
                .map(categoryType -> {
                    Item firstItem = itemPort.retrieveFirstByCategoryType(categoryType);
                    NextWal nextWal = NextWal.newInstance(userId, categoryType, firstItem);
                    return nextWalRepository.save(nextWal);
                })
                .collect(Collectors.toList());
        return new NextWals(nextWals);
    }

    @Override
    public void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType) {
        Long countOfCorrespondCategoryType = itemPort.countAllCorrespondItemsByCategoryType(categoryType);
        double nextItemId = nextWals.calculateNextItemId(randomNextWal, countOfCorrespondCategoryType);
        Item nextItem = itemPort.retrieveNextItemByCategoryTypeAndNextItemId(categoryType, nextItemId);
        nextWalRepository.updateNextWalItem(randomNextWal.getId(), nextItem);
        randomNextWal.updateItem(nextItem);
        nextWals.updateNextWalInfo(randomNextWal);
    }

    @Override
    public void deleteNextWalsByCanceledCategoryTypes(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        nextWalRepository.deleteAllInBatch(
                nextWalRepository.findNextWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId));
    }
}
