package backend.wal.auth.adapter.oauth.kakao;

import backend.wal.auth.adapter.oauth.kakao.dto.KakaoUserInfoResponse;
import backend.wal.auth.application.port.OAuthApiClientPort;
import backend.wal.auth.application.port.dto.OAuthUserInfoResponseDto;
import backend.wal.utils.HttpHeaderUtils;
import org.springframework.stereotype.Component;

@Component
public final class KakaoApiClientAdapter implements OAuthApiClientPort {

    private final KakaoApiClientCaller kakaoApiClientCaller;

    public KakaoApiClientAdapter(final KakaoApiClientCaller kakaoApiClientCaller) {
        this.kakaoApiClientCaller = kakaoApiClientCaller;
    }

    @Override
    public OAuthUserInfoResponseDto getOAuthUserId(String token) {
        String bearerToken = HttpHeaderUtils.withBearerToken(token);
        KakaoUserInfoResponse kakaoUserInfo = kakaoApiClientCaller.getKakaoUserInfo(bearerToken);
        return new OAuthUserInfoResponseDto(kakaoUserInfo.getId(), kakaoUserInfo.getNickname());
    }
}