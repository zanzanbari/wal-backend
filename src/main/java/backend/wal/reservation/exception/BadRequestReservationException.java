package backend.wal.reservation.exception;

import backend.wal.advice.exception.BadRequestException;

public final class BadRequestReservationException extends BadRequestException {

    private BadRequestReservationException(final String message) {
        super(message);
    }

    public static BadRequestException notDone(Long reservationId) {
        return new BadRequestReservationException(String.format("아직 전송이 완료되지 않은 왈소리 (%s) 입니다", reservationId));
    }
}
