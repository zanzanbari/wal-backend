package backend.wal.advice.dto;

public class ExceptionResponse {

    private final int statusCode;
    private final String message;

    public ExceptionResponse(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
