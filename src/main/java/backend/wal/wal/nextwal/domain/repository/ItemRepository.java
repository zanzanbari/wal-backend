package backend.wal.wal.nextwal.domain.repository;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.nextwal.domain.aggregate.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findFirstByCategoryCategoryType(WalCategoryType categoryType);

    Long countAllByCategoryCategoryType(WalCategoryType categoryType);

    Item findByCategoryCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, double nextItemId);
}
