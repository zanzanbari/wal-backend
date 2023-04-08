package backend.wal.auth.adapter.oauth.kakao.dto;

public class KakaoAccount {

    private Profile profile;

    private KakaoAccount() {
    }

    public KakaoAccount(final Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getProfileNickname() {
        return profile.getNickname();
    }
}
