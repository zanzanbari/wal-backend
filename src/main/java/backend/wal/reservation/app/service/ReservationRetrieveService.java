package backend.wal.reservation.app.service;

import backend.wal.reservation.app.dto.ReservationCalendarResponseDto;
import backend.wal.reservation.controller.dto.ReservationCalendarResponse;
import backend.wal.reservation.controller.dto.ReservationHistoryResponse;
import backend.wal.reservation.domain.Reservations;
import backend.wal.reservation.domain.aggregate.entity.Reservation;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.exception.ConflictReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationRetrieveService {

    private final ReservationRepository reservationRepository;
    private final Clock clock;

    public ReservationHistoryResponse retrieveReservationHistory(Long userId) {
        List<Reservation> allReservations = reservationRepository.findReservationsByUserId(userId);
        validateReservationExists(allReservations);

        Reservations reservations = new Reservations(allReservations);
        return new ReservationHistoryResponse(
                reservations.extractNotDoneReservation(),
                reservations.extractDoneReservation()
        );
    }

    public List<ReservationCalendarResponse> retrieveReservationDate(Long userId) {
        List<Reservation> reservationsAfterNow = reservationRepository
                .findReservationsBySendDueDateAfterAndUserId(LocalDateTime.now(clock), userId);
        validateReservationExists(reservationsAfterNow);

        Reservations reservations = new Reservations(reservationsAfterNow);
        return reservations.extractDateForCalender()
                .stream()
                .map(ReservationCalendarResponseDto::toWebResponse)
                .collect(Collectors.toList());
    }

    private void validateReservationExists(List<Reservation> findReservations) {
        if (findReservations.isEmpty()) {
            throw ConflictReservationException.none();
        }
    }
}
