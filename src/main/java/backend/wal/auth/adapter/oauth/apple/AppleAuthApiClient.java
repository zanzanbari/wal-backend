package backend.wal.auth.adapter.oauth.apple;

import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKeyResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "AppleAuthApiClient", url = "https://appleid.apple.com/auth")
public interface AppleAuthApiClient {

    @GetMapping("/keys")
    ApplePublicKeyResponse getPublicKeys();
}
