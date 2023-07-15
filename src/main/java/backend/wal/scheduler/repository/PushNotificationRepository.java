package backend.wal.scheduler.repository;

import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PushNotificationRepository extends JpaRepository<TodayWal, Long> {

    @Query("SELECT new backend.wal.scheduler.repository.ContentsAndFcmTokenResult(tw.message, ft.value)" +
            "FROM TodayWal tw " +
            "JOIN FETCH FcmToken ft " +
            "ON tw.userId = ft.userId " +
            "WHERE tw.timeType = :timeType " +
            "ORDER BY tw.userId")
    List<ContentsAndFcmTokenResult> findTodayWalMessageWithUserIdByTimeType(WalTimeType timeType);
}
