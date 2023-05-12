package backend.wal.advice.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends WalException {

    protected ForbiddenException(final String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
