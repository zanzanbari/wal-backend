package backend.wal.auth.adapter.oauth.kakao;

import backend.wal.auth.adapter.oauth.kakao.dto.KakaoUserInfoResponse;
import backend.wal.support.utils.HttpHeaderUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(value = "KakaoApiClientCaller", url = "https://kapi.kakao.com")
public interface KakaoApiClientCaller {

    @GetMapping("/v2/user/me")
    KakaoUserInfoResponse getKakaoUserInfo(@RequestHeader(HttpHeaderUtils.AUTHORIZATION) String bearerAccessToken);
}
