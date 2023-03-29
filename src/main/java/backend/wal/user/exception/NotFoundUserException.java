package backend.wal.user.exception;

import backend.wal.advice.exception.NotFoundException;

public final class NotFoundUserException extends NotFoundException {

    private NotFoundUserException(final String message) {
        super(message);
    }

    public static NotFoundException notExists(Long userId) {
        return new NotFoundUserException(String.format("존재하지 않는 유저 %s 입니다", userId));
    }
}
