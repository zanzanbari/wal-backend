package backend.wal.reservation.domain.repository;

import backend.wal.reservation.domain.aggregate.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsReservationBySendDueDateBetweenAndUserId(LocalDateTime start, LocalDateTime end, Long userId);
}
