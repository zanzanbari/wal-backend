package backend.wal.reservation.exception;

import backend.wal.advice.exception.NotFoundException;

public final class NotFoundReservationException extends NotFoundException {

    private NotFoundReservationException(final String message) {
        super(message);
    }

    public static NotFoundException none() {
        return new NotFoundReservationException("유저의 예약한 왈소리가 없습니다");
    }

    public static NotFoundException notExists(Long reservationId) {
        return new NotFoundReservationException(String.format("존재하지 않거나 이미 삭제된 히스토리 (%s) 입니다", reservationId));
    }
}
