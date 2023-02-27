package backend.wal.advice.exception;

import org.springframework.http.HttpStatus;

public abstract class WalException extends RuntimeException {

    private final HttpStatus status;

    protected WalException(final String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
