package backend.wal.auth.infrastructure.kakao.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class KakaoAccount {

    private Profile profile;

    public KakaoAccount(final Profile profile) {
        this.profile = profile;
    }

    public String getProfileNickname() {
        return profile.getNickname();
    }
}
