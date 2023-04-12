package backend.wal.auth.adapter.oauth.kakao.dto;

public class Profile {

    private String nickname;

    private Profile() {
    }

    public Profile(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
