package backend.wal.wal.onboarding.exception;

import backend.wal.advice.exception.ConflictException;

public final class ConflictOnboardingException extends ConflictException {

    public ConflictOnboardingException(final String message) {
        super(message);
    }

    public static ConflictException alreadyExists(Long userId) {
        return new ConflictOnboardingException(String.format("이미 온보딩 정보 세팅 완료된 유저 (%s) 입니다", userId));
    }
}
