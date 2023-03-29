package backend.wal.auth.application.port;

import backend.wal.auth.application.port.dto.OAuthUserInfoResponseDto;

public interface OAuthApiClientPort {

    OAuthUserInfoResponseDto getOAuthUserId(String token);
}
