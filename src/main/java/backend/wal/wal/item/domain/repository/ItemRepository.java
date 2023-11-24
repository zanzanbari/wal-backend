package backend.wal.wal.item.domain.repository;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT new backend.wal.wal.item.domain.repository.FirstItemsResult(i, c.categoryType) " +
            "FROM Item i " +
            "LEFT JOIN Category c ON i.category.id = c.id " +
            "WHERE i.categoryItemNumber = 1 AND c.categoryType IN (:categoryTypes)"
    )
    List<FirstItemsResult> findFirstItemsByCategoryTypes(Iterable<WalCategoryType> categoryTypes);

    Long countAllByCategoryCategoryType(WalCategoryType categoryType);

    Item findByCategoryCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, double nextItemId);
}
