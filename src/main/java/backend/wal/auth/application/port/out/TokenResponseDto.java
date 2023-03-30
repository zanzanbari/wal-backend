package backend.wal.auth.application.port.out;

public final class TokenResponseDto {

    private final String accessToken;
    private final String refreshToken;

    public TokenResponseDto(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
