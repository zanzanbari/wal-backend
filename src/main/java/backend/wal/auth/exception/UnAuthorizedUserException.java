package backend.wal.auth.exception;

import backend.wal.advice.exception.UnAuthorizedException;

public class UnAuthorizedUserException extends UnAuthorizedException {

    private UnAuthorizedUserException(final String message) {
        super(message);
    }

    public static UnAuthorizedException resignUser() {
        return new UnAuthorizedUserException("탈퇴한지 24시간이 지나지 않아 재가입 할 수 없습니다");
    }
}
