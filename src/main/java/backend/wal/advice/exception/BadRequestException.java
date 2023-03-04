package backend.wal.advice.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends WalException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
