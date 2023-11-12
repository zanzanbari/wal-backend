package backend.wal.auth.exception;

import backend.wal.advice.exception.UnAuthorizedException;

public class UnAuthorizedRoleException extends UnAuthorizedException {

    public UnAuthorizedRoleException(String message) {
        super(message);
    }

    public static UnAuthorizedException wrong(String role) {
        return new UnAuthorizedRoleException(String.format("접근 권한이 없는 Role 입니다. 요구되는 Role : (%s)", role));
    }
}
