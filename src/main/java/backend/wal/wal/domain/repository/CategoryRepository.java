package backend.wal.wal.domain.repository;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.wal.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryType(WalCategoryType categoryType);
}
