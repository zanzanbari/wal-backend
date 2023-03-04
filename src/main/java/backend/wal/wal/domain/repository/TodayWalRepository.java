package backend.wal.wal.domain.repository;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.wal.domain.entity.TodayWal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TodayWalRepository extends JpaRepository<TodayWal, Long> {

    List<TodayWal> findTodayWalsByUserId(Long userId);

    List<TodayWal> findTodayWalsByTimeTypeInAndUserId(Set<WalTimeType> timeTypes, Long userId);

    List<TodayWal> findTodayWalsByCategoryTypeInAndUserId(Set<WalCategoryType> categoryTypes, Long userId);
}
