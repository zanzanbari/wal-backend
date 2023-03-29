package backend.wal.auth.adapter.oauth.apple;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "AppleAuthApiClient", url = "https://appleid.apple.com/auth")
public interface AppleAuthApiClient {
}
