package backend.wal.notification.exception;

import backend.wal.advice.exception.InternalServerException;

public final class FCMException extends InternalServerException {

    public FCMException(final String message) {
        super(message);
    }

    public static InternalServerException sendAsyncException(String message) {
        return new FCMException(String.format("비동기 메시지 전송 중 예외 발생 (%s)", message));
    }

    public static InternalServerException threadInterruptException(String message) {
        return new FCMException(String.format("BackOff 수행 중 thread sleep interrupt 예외 발생 (%s)", message));

    }
}
