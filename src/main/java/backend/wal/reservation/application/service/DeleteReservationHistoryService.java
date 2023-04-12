package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.in.DeleteReservationHistoryUseCase;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.exception.BadRequestReservationException;
import backend.wal.reservation.exception.NotFoundReservationException;
import backend.wal.support.annotation.AppService;

@AppService
public class DeleteReservationHistoryService implements DeleteReservationHistoryUseCase {

    private final ReservationRepository reservationRepository;

    public DeleteReservationHistoryService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void deleteHistory(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> NotFoundReservationException.notExists(reservationId));
        validateDoneReservation(reservation);
        reservationRepository.deleteById(reservationId);
    }

    private void validateDoneReservation(Reservation reservation) {
        if (reservation.isNotDone()) {
            throw BadRequestReservationException.notDone(reservation.getId());
        }
    }
}
