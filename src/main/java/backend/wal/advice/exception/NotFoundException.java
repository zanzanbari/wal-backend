package backend.wal.advice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends WalException {

    public NotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
