package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.in.UpdateReservationUseCase;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.exception.NotFoundReservationException;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class UpdateReservationService implements UpdateReservationUseCase {

    private final ReservationRepository reservationRepository;

    public UpdateReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public void updateSendStatusToDone(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> NotFoundReservationException.notExists(reservationId));
        reservation.finish();
    }
}
