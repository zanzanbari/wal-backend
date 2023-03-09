package backend.wal.auth.application.dto.response;

import lombok.Getter;

@Getter
public final class TokenResponse {

    private final String accessToken;
    private final String refreshToken;

    public TokenResponse(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
