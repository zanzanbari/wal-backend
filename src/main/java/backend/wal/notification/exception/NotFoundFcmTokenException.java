package backend.wal.notification.exception;

import backend.wal.advice.exception.NotFoundException;

public final class NotFoundFcmTokenException extends NotFoundException {

    private NotFoundFcmTokenException(final String message) {
        super(message);
    }

    public static NotFoundException notExists(Long userId) {
        return new NotFoundFcmTokenException(String.format("Fcm Token 이 존재하지 않습니다 (유저: %s)", userId));
    }
}
