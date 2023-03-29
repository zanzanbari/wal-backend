package backend.wal.onboard.todaywal.exception;

import backend.wal.advice.exception.NotFoundException;

public final class NotFoundTodayWalException extends NotFoundException {

    private NotFoundTodayWalException(final String message) {
        super(message);
    }

    public static NotFoundException notExist(Long todayWalId, Long userId) {
        return new NotFoundTodayWalException(String.format(
                "유저 (%s)의 해당 왈소리 (%s)가 존재하지 않습니다", userId, todayWalId));
    }
}
