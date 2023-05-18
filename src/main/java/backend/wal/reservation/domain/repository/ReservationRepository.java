package backend.wal.reservation.domain.repository;

import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.aggregate.SendStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsReservationBySendDueDateBetweenAndUserId(LocalDateTime start, LocalDateTime end, Long userId);

    List<Reservation> findReservationsByUserId(Long userId);

    List<Reservation> findReservationsBySendDueDateAfterAndUserId(LocalDateTime now, Long userId);

    Optional<Reservation> findReservationBySendDueDateBetweenAndUserId(LocalDateTime today,
                                                                       LocalDateTime tomorrow,
                                                                       Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.sendDueDate > :now AND r.sendStatus = :notDone")
    List<Reservation> findNotDoneReservationAfterNow(LocalDateTime now, SendStatus notDone);
}
