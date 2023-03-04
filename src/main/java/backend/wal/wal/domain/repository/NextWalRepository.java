package backend.wal.wal.domain.repository;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.wal.domain.entity.NextWal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface NextWalRepository extends JpaRepository<NextWal, Long> {

    List<NextWal> findNextWalsByUserId(Long userId);

    List<NextWal> findNextWalsByCategoryTypeInAndUserId(Set<WalCategoryType> categoryTypes, Long userId);
}
