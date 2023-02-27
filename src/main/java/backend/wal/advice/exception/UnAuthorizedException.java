package backend.wal.advice.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends WalException {

    protected UnAuthorizedException(final String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
