package backend.wal.admin.exception;

import backend.wal.advice.exception.UnAuthorizedException;

public class UnAuthorizedPasswordException extends UnAuthorizedException {

    public UnAuthorizedPasswordException(String message) {
        super(message);
    }

    public static UnAuthorizedPasswordException wrong() {
        return new UnAuthorizedPasswordException("비밀번호가 일치하지 않습니다");
    }
}
