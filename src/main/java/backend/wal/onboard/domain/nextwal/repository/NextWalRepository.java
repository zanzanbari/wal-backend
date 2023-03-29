package backend.wal.onboard.domain.nextwal.repository;

import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.nextwal.aggregate.NextWal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface NextWalRepository extends JpaRepository<NextWal, Long> {

    List<NextWal> findNextWalsByUserId(Long userId);

    List<NextWal> findNextWalsByCategoryTypeInAndUserId(Set<WalCategoryType> categoryTypes, Long userId);
}
