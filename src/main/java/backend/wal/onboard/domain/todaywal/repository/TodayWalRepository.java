package backend.wal.onboard.domain.todaywal.repository;

import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.common.WalTimeType;
import backend.wal.onboard.domain.todaywal.aggregate.TodayWal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TodayWalRepository extends JpaRepository<TodayWal, Long> {

    List<TodayWal> findTodayWalsByUserId(Long userId);

    List<TodayWal> findTodayWalsByTimeTypeInAndUserId(Set<WalTimeType> timeTypes, Long userId);

    List<TodayWal> findTodayWalsByCategoryTypeInAndUserId(Set<WalCategoryType> categoryTypes, Long userId);

    List<TodayWal> findTodayWalByUserIdInAndTimeType(List<Long> userIds, WalTimeType timeType);

    Optional<TodayWal> findTodayWalByIdAndUserId(Long todayWalId, Long userId);
}
