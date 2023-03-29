package backend.wal.auth.application.port.dto;

public final class OAuthUserInfoResponseDto {

    private final String id;
    private final String nickname;

    public OAuthUserInfoResponseDto(final String id, final String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
