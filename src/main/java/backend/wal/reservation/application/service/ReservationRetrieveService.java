package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.in.dto.ReservationCalendarResponseDto;
import backend.wal.reservation.application.port.in.ReservationRetrieveUseCase;
import backend.wal.reservation.web.dto.ReservationCalendarResponse;
import backend.wal.reservation.web.dto.ReservationHistoryResponse;
import backend.wal.reservation.domain.Reservations;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.exception.NotFoundReservationException;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AppService
@Transactional(readOnly = true)
public class ReservationRetrieveService implements ReservationRetrieveUseCase {

    private final ReservationRepository reservationRepository;
    private final Clock clock;

    public ReservationRetrieveService(final ReservationRepository reservationRepository, final Clock clock) {
        this.reservationRepository = reservationRepository;
        this.clock = clock;
    }

    @Override
    public ReservationHistoryResponse retrieveReservationHistory(Long userId) {
        List<Reservation> allReservations = reservationRepository.findReservationsByUserId(userId);
        validateReservationExists(allReservations);

        Reservations reservations = new Reservations(allReservations);
        return new ReservationHistoryResponse(
                reservations.extractNotDoneReservation(),
                reservations.extractDoneReservation()
        );
    }

    @Override
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
            throw NotFoundReservationException.none();
        }
    }

    @Override
    public Optional<Reservation> retrieveReservationBetweenTodayAndTomorrow(Long userId) {
        long ONE_DAY = 1;
        LocalDateTime today = LocalDate.now(clock).atStartOfDay();
        LocalDateTime tomorrow = today.plusDays(ONE_DAY);
        return reservationRepository.findReservationBySendDueDateBetweenAndUserId(today, tomorrow, userId);
    }
}
