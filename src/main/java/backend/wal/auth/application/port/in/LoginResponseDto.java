package backend.wal.auth.application.port.in;

public final class LoginResponseDto {

    private final Long userId;
    private final String nickname;
    private final boolean isNewUser;

    public LoginResponseDto(final Long userId, final String nickname, final boolean isNewUser) {
        this.userId = userId;
        this.nickname = nickname;
        this.isNewUser = isNewUser;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isNewUser() {
        return isNewUser;
    }
}