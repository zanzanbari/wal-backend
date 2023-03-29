package backend.wal.onboard.nextwal.domain.repository;

import backend.wal.onboard.common.WalCategoryType;
import backend.wal.onboard.nextwal.domain.aggregate.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryType(WalCategoryType categoryType);
}
