package backend.wal.auth.exception;

import backend.wal.advice.exception.UnAuthorizedException;

public final class UnAuthorizedTokenException extends UnAuthorizedException {

    private UnAuthorizedTokenException(final String message) {
        super(message);
    }

    public static UnAuthorizedException wrong(String token) {
        return new UnAuthorizedTokenException(String.format("잘못된 토큰 (%s) 입니다", token));
    }

    public static UnAuthorizedException expired(String token) {
        return new UnAuthorizedTokenException(String.format("만료된 토큰 (%s) 입니다", token));
    }

    public static UnAuthorizedException tokenNotFound(String token) {
        return new UnAuthorizedTokenException(String.format("헤더의 토큰 값 (%s) 를 찾을 수 없습니다", token));
    }

    public static UnAuthorizedException authenticationTypeNotFound() {
        return new UnAuthorizedTokenException("Authorization 인증 타입 Bearer 가 누락되었습니다");
    }

    public static UnAuthorizedException unMatched() {
        return new UnAuthorizedTokenException("ApplePublicKey 와 일치하는 값을 찾을 수 없습니다");
    }

    public static UnAuthorizedException expired() {
        return new UnAuthorizedTokenException("social token 이 만료되었습니다");
    }
}
