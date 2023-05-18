package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;
import backend.wal.reservation.application.port.in.dto.RegisterReservationResponseDto;
import backend.wal.reservation.application.port.in.ReservationHandlerUseCase;
import backend.wal.reservation.application.port.out.TodayWalPort;
import backend.wal.reservation.application.port.out.ReservationTodayWalRequestDto;
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
public class ReservationHandlerService implements ReservationHandlerUseCase {

    private final ReservationRepository reservationRepository;
    private final TodayWalPort todayWalPort;
    private final Clock clock;

    public ReservationHandlerService(final ReservationRepository reservationRepository,
                                     final TodayWalPort todayWalPort,
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
        if (hasReservationBetweenDate(startDay, endDay, userId)) {
            throw ConflictReservationException.alreadyExistDate(startDay.toLocalDate());
        }
    }

    private void registerTodayWalIfToday(Reservation reservation) {
        if (reservation.isToday(LocalDate.now(clock))) {
            todayWalPort.registerReservationCall(
                    new ReservationTodayWalRequestDto(
                            reservation.getId(),
                            reservation.getMessage(),
                            reservation.getSendDueDate()
                    ));
        }
    }

    @Override
    @Transactional
    public void deleteIfCanceledReservationIsToday(Long userId) {
        LocalDateTime today = LocalDate.now(clock).atStartOfDay();
        LocalDateTime tomorrow = today.plusDays(1);
        if (hasReservationBetweenDate(today, tomorrow, userId)) { // FIXME : 예약날짜에 해당하는 TodayWal 이 있을때 삭제해야함
            todayWalPort.deleteReservationCall(userId);
        }
    }

    private boolean hasReservationBetweenDate(LocalDateTime start, LocalDateTime end, Long userId) {
        return reservationRepository.existsReservationBySendDueDateBetweenAndUserId(start, end, userId);
    }
}
