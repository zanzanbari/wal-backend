package backend.wal.auth.exception;

import backend.wal.advice.exception.ForbiddenException;

public final class ForbiddenUserException extends ForbiddenException {

    private ForbiddenUserException(final String message) {
        super(message);
    }

    public static ForbiddenException resignUser() {
        return new ForbiddenUserException("탈퇴한지 24시간이 지나지 않아 재가입 할 수 없습니다");
    }
}
