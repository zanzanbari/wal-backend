package backend.wal.auth.application.port.in;

public final class LoginResponseDto {

    private final Long userId;
    private final boolean isNewUser;

    public LoginResponseDto(final Long userId, final boolean isNewUser) {
        this.userId = userId;
        this.isNewUser = isNewUser;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isNewUser() {
        return isNewUser;
    }
}