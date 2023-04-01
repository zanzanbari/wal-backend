package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.out.TodayWalPort;
import backend.wal.reservation.application.port.in.RegisterReservationUseCase;
import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;
import backend.wal.reservation.application.port.in.dto.RegisterReservationResponseDto;
import backend.wal.reservation.domain.ReservationTime;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.exception.ConflictReservationException;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AppService
public class RegisterReservationService implements RegisterReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final TodayWalPort todayWalPort;
    private final Clock clock;

    public RegisterReservationService(final ReservationRepository reservationRepository, final TodayWalPort todayWalPort,
                                      final Clock clock) {
        this.reservationRepository = reservationRepository;
        this.todayWalPort = todayWalPort;
        this.clock = clock;
    }

    @Override
    @Transactional
    public RegisterReservationResponseDto register(AddReservationRequestDto requestDto) {
        ReservationTime reservationTime = ReservationTime.startDayFrom(requestDto.getSendDate());
        validateReservationDate(reservationTime, requestDto.getUserId());
        Reservation reservation = reservationRepository.save(Reservation.newInstance(requestDto));
        registerTodayWalIfToday(reservation);
        return new RegisterReservationResponseDto(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getMessage(),
                reservation.getSendDueDate(),
                reservation.getDelayTimeAboutNow(LocalDateTime.now(clock))
        );
    }

    private void validateReservationDate(ReservationTime reservationTime, Long userId) {
        LocalDateTime startDay = reservationTime.getReservationDate();
        LocalDateTime endDay = reservationTime.getNextDate();
        if (reservationRepository.existsReservationBySendDueDateBetweenAndUserId(startDay, endDay, userId)) {
            throw ConflictReservationException.alreadyExistDate(startDay.toLocalDate());
        }
    }

    private void registerTodayWalIfToday(Reservation reservation) {
        if (reservation.isToday(LocalDate.now(clock))) {
            todayWalPort.registerReservationCall(reservation.getUserId(), reservation.getMessage());
        }
    }
}
