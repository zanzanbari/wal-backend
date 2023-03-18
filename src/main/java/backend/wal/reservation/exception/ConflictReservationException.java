package backend.wal.reservation.exception;

import backend.wal.advice.exception.ConflictException;

import java.time.LocalDate;

public final class ConflictReservationException extends ConflictException {

    private ConflictReservationException(final String message) {
        super(message);
    }

    public static ConflictException alreadyExistDate(LocalDate sendDate) {
        return new ConflictReservationException(String.format("이미 존재하는 예약 날짜 (%s) 입니다", sendDate));
    }

    public static ConflictException none() {
        return new ConflictReservationException("유저의 예약한 왈소리가 없습니다");
    }
}
