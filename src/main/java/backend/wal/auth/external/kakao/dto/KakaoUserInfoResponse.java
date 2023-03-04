package backend.wal.auth.external.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class KakaoUserInfoResponse {

    private final String id;
    private final KakaoAccount kakaoAccount;

    public KakaoUserInfoResponse(final String id, final KakaoAccount kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class KakaoAccount {
        private final Profile profile;
        @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
        private static class Profile {
            private final String nickname;
        }
    }
}
