package backend.wal.reservation.application.service;

import backend.wal.wal.todaywal.application.port.RegisterReservationTodayWalUseCase;
import backend.wal.reservation.application.port.RegisterReservationUseCase;
import backend.wal.reservation.application.port.dto.AddReservationRequestDto;
import backend.wal.reservation.application.port.dto.RegisterReservationResponseDto;
import backend.wal.reservation.domain.ReservationTime;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.exception.ConflictReservationException;
import lombok.RequiredArgsConstructor;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AppService
@RequiredArgsConstructor
public class ReservationService implements RegisterReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final RegisterReservationTodayWalUseCase reservationTodayWalUseCase;
    private final Clock clock;

    @Override
    @Transactional
    public RegisterReservationResponseDto register(AddReservationRequestDto requestDto) {
        ReservationTime reservationTime = ReservationTime.startDayFrom(requestDto.getSendDate());
        validateReservationDate(reservationTime, requestDto.getUserId());
        Reservation reservation = reservationRepository.save(Reservation.newInstance(requestDto));
        registerTodayWalIfToday(reservation); // FIXME 이건 이벤트 기반이 맞을듯 ㅇㅇ
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
            reservationTodayWalUseCase.registerReservationTodayWal(reservation.getUserId(), reservation.getMessage());
        }
    }
}
