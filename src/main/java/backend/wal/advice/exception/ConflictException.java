package backend.wal.advice.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends WalException {

    public ConflictException(final String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
