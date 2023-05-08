package backend.wal.wal.todaywal.domain.repository;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TodayWalRepository extends JpaRepository<TodayWal, Long> {

    List<TodayWal> findTodayWalsByUserIdOrderBySendTimeAsc(Long userId);

    List<TodayWal> findTodayWalsByTimeTypeInAndUserId(Set<WalTimeType> timeTypes, Long userId);

    List<TodayWal> findTodayWalsByCategoryTypeInAndUserId(Set<WalCategoryType> categoryTypes, Long userId);

    List<TodayWal> findTodayWalByUserIdInAndTimeType(List<Long> userIds, WalTimeType timeType);

    Optional<TodayWal> findTodayWalByIdAndUserId(Long todayWalId, Long userId);

    void deleteTodayWalByUserIdAndCategoryType(Long userId, WalCategoryType categoryType);
}
