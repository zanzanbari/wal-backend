package backend.wal.auth.infrastructure.apple;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "AppleAuthApiClient", url = "https://appleid.apple.com/auth")
public interface AppleAuthApiClient {
}
