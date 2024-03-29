package backend.wal.auth.exception;

import backend.wal.advice.exception.InternalServerException;

import java.lang.reflect.Method;

public final class InternalAuthServerException extends InternalServerException {

    private InternalAuthServerException(final String message) {
        super(message);
    }

    public static InternalServerException annotationNotFound() {
        return new InternalAuthServerException("@Authentication 어노테이션이 필요한 컨트롤러 입니다");
    }

    public static InternalServerException attributeNotFound(String attribute, Class<?> clazz, Method method) {
        return new InternalAuthServerException(String.format(
                "(%s) 를 가져오지 못했습니다 (%s - %s)", attribute, clazz, method));
    }

    public static InternalServerException cannotGeneratePublicKey() {
        return new InternalAuthServerException("가져온 ApplePublicKey 값으로 PublicKey 생성에 실패했습니다");
    }

    public static InternalServerException anotherFeignException() {
        return new InternalAuthServerException("Feign 통신간 Exception 이 발생했습니다");
    }
}
