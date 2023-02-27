package backend.wal.advice.dto;

public class ExceptionResponse {

    private final String message;

    private ExceptionResponse(final String message) {
        this.message = message;
    }

    public static ExceptionResponse create(final String message) {
        return new ExceptionResponse(message);
    }
}
