package backend.wal.reservation.domain.repository;

import backend.wal.reservation.domain.aggregate.ScheduledMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage, Long> {

    @Query("SELECT DISTINCT sm FROM ScheduledMessage sm WHERE sm.sendDueDate > :now")
    List<ScheduledMessage> findScheduledMessagesBySendDueDateAfter(LocalDateTime now);
}
