package backend.wal.auth.application.port.out;

public interface OAuthApiClientPort {

    OAuthUserInfoResponseDto getOAuthUserId(String token);
}
