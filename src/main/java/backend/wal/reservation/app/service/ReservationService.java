package backend.wal.reservation.app.service;

import backend.wal.reservation.app.dto.AddReservationRequestDto;
import backend.wal.reservation.app.dto.RegisterReservationResponseDto;
import backend.wal.reservation.domain.ReservationTime;
import backend.wal.reservation.domain.aggregate.entity.Reservation;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.exception.ConflictReservationException;
import backend.wal.wal.service.TodayWalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TodayWalService todayWalService;
    private final Clock clock;

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
            todayWalService.registerReservationTodayWal(reservation.getUserId(), reservation.getMessage());
        }
    }
}
