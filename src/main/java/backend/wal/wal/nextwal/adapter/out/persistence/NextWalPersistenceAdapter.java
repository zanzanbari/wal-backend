package backend.wal.wal.nextwal.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.Category;
import backend.wal.wal.nextwal.application.port.out.NextWalPersistencePort;
import backend.wal.wal.item.domain.Item;
import backend.wal.wal.nextwal.domain.NextWal;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NextWalPersistenceAdapter implements NextWalPersistencePort {

    private final NextWalRepository nextWalRepository;

    public NextWalPersistenceAdapter(NextWalRepository nextWalRepository) {
        this.nextWalRepository = nextWalRepository;
    }

    @Override
    public List<NextWal> saveAll(List<Item> items, Long userId) {
        Map<Long, Item> itemMap = items.stream()
                .collect(Collectors.toMap(Item::getId, Function.identity()));

        List<NextWalEntity> nextWalEntities = itemMap.values()
                .stream()
                .map(item -> new NextWalEntity(userId, item.getCategoryType(), item.getId()))
                .collect(Collectors.toList());
        nextWalRepository.saveAllInBatch(nextWalEntities);

        return nextWalEntities.stream()
                .map(nextWalEntity -> new NextWal(
                        nextWalEntity.getId(),
                        nextWalEntity.getUserId(),
                        nextWalEntity.getCategoryType(),
                        itemMap.get(nextWalEntity.getItemId())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void updateNextWalItem(Long nextWalId, Long nextItemId) {
        nextWalRepository.updateNextWalItem(nextItemId, nextWalId);
    }

    @Override
    public void deleteAllInBatchBy(Collection<WalCategoryType> canceledCategoryTypes, Long userId) {
        nextWalRepository.deleteAllInBatch(
                nextWalRepository.findNextWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId)
        );
    }

    @Override
    public List<NextWal> findNextWalsByUserId(Long userId) {
        return nextWalRepository.findNextWalsByUserId(userId)
                .stream()
                .map(nextWalWithItem -> NextWal.from(
                        nextWalWithItem.getNextWalEntity(),
                        Item.of(
                                nextWalWithItem.getItemEntity(),
                                new Category(nextWalWithItem.getNextWalEntity().getCategoryType())
                        ))
                )
                .collect(Collectors.toList());
    }
}
