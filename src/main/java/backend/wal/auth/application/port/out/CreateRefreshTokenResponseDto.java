package backend.wal.auth.application.port.out;

import java.util.Date;

public final class CreateRefreshTokenResponseDto {

    private final Long userId;
    private final String tokenValue;
    private final Date refreshTokenExpiresIn;

    public CreateRefreshTokenResponseDto(final Long userId, final String tokenValue, final Date refreshTokenExpiresIn) {
        this.userId = userId;
        this.tokenValue = tokenValue;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Date getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }
}
