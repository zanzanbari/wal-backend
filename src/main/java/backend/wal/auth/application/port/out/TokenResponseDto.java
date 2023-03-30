package backend.wal.auth.web.dto;

public final class TokenResponse {

    private final String accessToken;
    private final String refreshToken;

    public TokenResponse(final String accessToken, final String refreshToken) {
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
