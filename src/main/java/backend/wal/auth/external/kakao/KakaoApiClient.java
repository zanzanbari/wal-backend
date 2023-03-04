package backend.wal.auth.external.kakao;

import backend.wal.auth.external.kakao.dto.KakaoUserInfoResponse;
import backend.wal.utils.HttpHeaderUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "KakaoApiClient", url = "https://kapi.kakao.com")
public interface KakaoApiClient {

    @GetMapping("/v2/user/me")
    KakaoUserInfoResponse getKakaoUserInfo(@RequestHeader(HttpHeaderUtils.AUTHORIZATION) String bearerAccessToken);
}
