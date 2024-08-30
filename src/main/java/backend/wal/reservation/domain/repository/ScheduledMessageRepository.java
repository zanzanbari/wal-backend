package backend.wal.reservation.domain.repository;

import backend.wal.reservation.domain.aggregate.ScheduledMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage, Long> {

    @Query("SELECT MAX(sm.downtime.groupId) FROM ScheduledMessage sm")
    Long findRecentDowntimeId();

    @Query("SELECT sm FROM ScheduledMessage sm " +
            "WHERE sm.sendDueDate > sm.downtime.value " +
            "AND sm.downtime.groupId = :recentDowntimeId")
    List<ScheduledMessage> findScheduledMessagesBySendDueDateAfter(Long recentDowntimeId);

    void deleteAllByUserId(Long userId);
}
