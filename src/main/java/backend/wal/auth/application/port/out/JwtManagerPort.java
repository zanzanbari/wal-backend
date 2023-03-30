package backend.wal.auth.application.port.out;

public interface JwtManagerPort {

    String createAccessToken(Long userId);

    CreateRefreshTokenResponseDto createRefreshToken(Long userId);

    void validateToken(String token);

    Long getLoginUserIdFromAccessToken(String accessToken);
}
