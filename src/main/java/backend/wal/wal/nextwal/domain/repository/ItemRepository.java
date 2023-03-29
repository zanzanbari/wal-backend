package backend.wal.onboard.nextwal.domain.repository;

import backend.wal.onboard.common.WalCategoryType;
import backend.wal.onboard.nextwal.domain.aggregate.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findFirstByCategoryCategoryType(WalCategoryType categoryType);

    Long countAllByCategoryCategoryType(WalCategoryType categoryType);

    Item findByCategoryCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, double nextItemId);
}
