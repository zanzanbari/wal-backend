package backend.wal.auth.infrastructure.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserInfoResponse {

    private String id;
    private KakaoAccount kakaoAccount;

    public KakaoUserInfoResponse(final String id, final KakaoAccount kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return kakaoAccount.getProfileNickname();
    }
}
