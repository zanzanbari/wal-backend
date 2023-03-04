package backend.wal.auth.exception;

import backend.wal.advice.exception.InternalServerException;
import org.springframework.core.MethodParameter;

public class InternalAuthServerException extends InternalServerException {

    private InternalAuthServerException(final String message) {
        super(message);
    }

    public static InternalServerException annotationNotFound() {
        return new InternalAuthServerException("@Authentication 어노테이션이 필요한 컨트롤러 입니다");
    }

    public static InternalServerException attributeNotFound(MethodParameter parameter) {
        return new InternalAuthServerException(String.format(
                "USER_ID 를 가져오지 못했습니다 (%s - %s)", parameter.getClass(), parameter.getMethod()));
    }
}
