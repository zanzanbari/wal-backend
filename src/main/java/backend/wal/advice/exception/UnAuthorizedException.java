package backend.wal.advice.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends WalException {

    public UnAuthorizedException(final String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
