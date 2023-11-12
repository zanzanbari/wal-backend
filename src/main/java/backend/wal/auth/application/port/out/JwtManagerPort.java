package backend.wal.auth.application.port.out;

public interface JwtManagerPort {

    String createAccessToken(Long userId, String role);

    CreateRefreshTokenResponseDto createRefreshToken(Long userId);

    void validateToken(String token);

    JwtPayloadInfo getLoginUserIdFromAccessToken(String accessToken);
}
