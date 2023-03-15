package backend.wal.rabbitmq.exception;

import backend.wal.advice.exception.InternalServerException;

public class InternalServerJsonException extends InternalServerException {

    private InternalServerJsonException(final String message) {
        super(message);
    }

    public static InternalServerException fail(String message) {
        return new InternalServerJsonException(String.format("JsonProcessing 에 실패했습니다 (%s)", message));
    }
}
