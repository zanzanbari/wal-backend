package backend.wal.wal.item.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findByCategoryType(WalCategoryType categoryType);
}
