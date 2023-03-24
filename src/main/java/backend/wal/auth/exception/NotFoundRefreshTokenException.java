package backend.wal.auth.exception;

import backend.wal.advice.exception.NotFoundException;

public final class NotFoundRefreshTokenException extends NotFoundException {

    private NotFoundRefreshTokenException(final String message) {
        super(message);
    }

    public static NotFoundException notExists() {
        return new NotFoundRefreshTokenException("존재하지 않는 refreshToken 입니다");
    }
}
