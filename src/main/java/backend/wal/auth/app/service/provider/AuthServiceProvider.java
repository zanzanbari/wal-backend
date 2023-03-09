package backend.wal.auth.application.service.provider;

import backend.wal.auth.application.service.AppleAuthService;
import backend.wal.auth.application.service.AuthService;
import backend.wal.auth.application.service.KakaoAuthService;
import backend.wal.user.domain.aggregate.entity.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthServiceProvider {

    private static final Map<SocialType, AuthService> AUTH_SERVICE_PROVIDER = new EnumMap<>(SocialType.class);

    private final AppleAuthService appleAuthService;
    private final KakaoAuthService kakaoAuthService;

    @PostConstruct
    public void initAuthServiceProvider() {
        AUTH_SERVICE_PROVIDER.put(SocialType.APPLE, appleAuthService);
        AUTH_SERVICE_PROVIDER.put(SocialType.KAKAO, kakaoAuthService);
    }

    public AuthService getAuthServiceBy(SocialType socialType) {
        return AUTH_SERVICE_PROVIDER.get(socialType);
    }
}
