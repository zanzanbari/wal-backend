package backend.wal.wal.repository;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.wal.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findFirstByCategoryCategoryType(WalCategoryType categoryType);

    Long countAllByCategoryCategoryType(WalCategoryType categoryType);

    Item findByCategoryCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, double nextItemId);
}
