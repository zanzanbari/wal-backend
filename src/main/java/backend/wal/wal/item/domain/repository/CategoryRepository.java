package backend.wal.wal.item.domain.repository;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryType(WalCategoryType categoryType);
}
