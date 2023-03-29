package backend.wal.onboard.exception;

import backend.wal.advice.exception.NotFoundException;

public final class NotFoundOnboardingException extends NotFoundException {

    private NotFoundOnboardingException(final String message) {
        super(message);
    }

    public static NotFoundException notExists(Long userId) {
        return new NotFoundOnboardingException(String.format("유저 (%s)의 온보딩 정보가 존재하지 않습니다", userId));
    }
}
