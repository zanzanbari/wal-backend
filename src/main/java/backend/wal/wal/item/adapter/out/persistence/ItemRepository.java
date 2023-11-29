package backend.wal.wal.item.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query("SELECT i FROM ItemEntity i " +
            "JOIN FETCH i.category c " +
            "WHERE i.categoryItemNumber = 1 " +
            "AND c.categoryType IN (:categoryTypes)")
    List<ItemEntity> findFirstItemsByCategoryTypes(Iterable<WalCategoryType> categoryTypes);

    Long countAllByCategoryCategoryType(WalCategoryType categoryType);

    @Query("SELECT i FROM ItemEntity i " +
            "JOIN FETCH i.category c " +
            "WHERE c.categoryType = :categoryType " +
            "AND i.categoryItemNumber = :nextItemId")
    ItemEntity findByCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, int nextItemId);
}
