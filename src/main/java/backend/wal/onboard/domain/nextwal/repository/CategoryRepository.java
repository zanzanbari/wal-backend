package backend.wal.onboard.domain.nextwal.repository;

import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.nextwal.aggregate.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryType(WalCategoryType categoryType);
}
