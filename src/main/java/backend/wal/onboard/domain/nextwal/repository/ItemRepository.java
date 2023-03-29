package backend.wal.onboard.domain.nextwal.repository;

import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.nextwal.aggregate.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findFirstByCategoryCategoryType(WalCategoryType categoryType);

    Long countAllByCategoryCategoryType(WalCategoryType categoryType);

    Item findByCategoryCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, double nextItemId);
}
