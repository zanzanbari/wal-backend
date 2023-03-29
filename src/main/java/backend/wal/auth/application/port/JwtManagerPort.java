package backend.wal.auth.application.port;

import backend.wal.auth.application.port.dto.CreateRefreshTokenResponseDto;

public interface JwtManagerPort {

    String createAccessToken(Long userId);

    CreateRefreshTokenResponseDto createRefreshToken(Long userId);

    void validateToken(String token);

    Long getLoginUserIdFromAccessToken(String accessToken);
}
