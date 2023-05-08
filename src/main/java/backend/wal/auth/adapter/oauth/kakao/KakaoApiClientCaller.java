package backend.wal.auth.adapter.oauth.kakao;

import static backend.wal.support.utils.HttpHeaderUtils.*;

import backend.wal.auth.adapter.oauth.kakao.dto.KakaoUserInfoResponse;
import backend.wal.auth.config.FeignExceptionDecoder;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "KakaoApiClientCaller", url = "https://kapi.kakao.com", configuration = FeignExceptionDecoder.class)
public interface KakaoApiClientCaller {

    @GetMapping("/v2/user/me")
    KakaoUserInfoResponse getKakaoUserInfo(@RequestHeader(AUTHORIZATION) String bearerAccessToken);
}
