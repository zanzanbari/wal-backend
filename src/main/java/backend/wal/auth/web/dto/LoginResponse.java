package backend.wal.auth.web.dto;

public class LoginResponse {

    private final String nickname;

    public LoginResponse(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
