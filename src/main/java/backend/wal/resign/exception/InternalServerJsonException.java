package backend.wal.resign.exception;

import backend.wal.advice.exception.InternalServerException;

public class InternalServerJsonException extends InternalServerException {

    private InternalServerJsonException(final String message) {
        super(message);
    }

    public static InternalServerException writeException(String attribute, String message) {
        return new InternalServerJsonException(String.format(
                "%s 를 JSON 으로의 convert 에 실패했습니다-(%s)", attribute, message));
    }

    public static InternalServerException readException(String dbData, String message) {
        return new InternalServerJsonException(String.format(
                "JSON %s 데이터를 객체로 convert 에 실패했습니다-(%s)", dbData, message));
    }
}
