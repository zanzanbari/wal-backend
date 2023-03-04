package backend.wal.advice.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends WalException {

    public InternalServerException(final String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
