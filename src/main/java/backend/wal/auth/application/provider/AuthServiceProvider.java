package backend.wal.auth.application.provider;

import backend.wal.auth.application.port.AuthUseCase;
import backend.wal.auth.application.service.AppleAuthService;
import backend.wal.auth.application.service.KakaoAuthService;
import backend.wal.user.domain.aggregate.SocialType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;

@Component
public final class AuthServiceProvider {

    private static final Map<SocialType, AuthUseCase> AUTH_SERVICE_PROVIDER = new EnumMap<>(SocialType.class);

    private final AppleAuthService appleAuthService;
    private final KakaoAuthService kakaoAuthService;

    public AuthServiceProvider(final AppleAuthService appleAuthService, final KakaoAuthService kakaoAuthService) {
        this.appleAuthService = appleAuthService;
        this.kakaoAuthService = kakaoAuthService;
    }

    @PostConstruct
    public void initAuthServiceProvider() {
        AUTH_SERVICE_PROVIDER.put(SocialType.APPLE, appleAuthService);
        AUTH_SERVICE_PROVIDER.put(SocialType.KAKAO, kakaoAuthService);
    }

    public AuthUseCase getAuthServiceBy(SocialType socialType) {
        return AUTH_SERVICE_PROVIDER.get(socialType);
    }
}
