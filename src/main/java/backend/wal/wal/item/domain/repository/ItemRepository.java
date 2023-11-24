package backend.wal.wal.item.domain.repository;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(
            "SELECT i FROM Item i " +
            "JOIN FETCH i.category c " +
            "WHERE i.categoryItemNumber = 1 AND c.categoryType IN (:categoryTypes)"
    )
    List<Item> findFirstItemsByCategoryTypes(Iterable<WalCategoryType> categoryTypes);

    Long countAllByCategoryCategoryType(WalCategoryType categoryType);

    Item findByCategoryCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, double nextItemId);
}
