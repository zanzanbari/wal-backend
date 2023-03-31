package backend.wal.auth.application.provider;

import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.service.AppleAuthService;
import backend.wal.auth.application.service.KakaoAuthService;
import backend.wal.user.domain.aggregate.SocialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthServiceProviderTest {

    @Mock
    private AppleAuthService appleAuthService;

    @Mock
    private KakaoAuthService kakaoAuthService;

    @InjectMocks
    private AuthServiceProvider authServiceProvider;

    @BeforeEach
    void setUp() {
        authServiceProvider.initAuthServiceProvider();
    }

    @DisplayName("Apple 소셜 타입을 받아 AppleAuthService 를 가져온다")
    @Test
    void getAuthServiceByApple() {
        // when
        AuthUseCase authUseCase = authServiceProvider.getAuthServiceBy(SocialType.APPLE);

        // then
        assertThat(authUseCase).isEqualTo(appleAuthService);
    }

    @DisplayName("Kakao 소셜 타입을 받아 KakaoAuthService 를 가져온다")
    @Test
    void getAuthServiceByKakao() {
        // when
        AuthUseCase authUseCase = authServiceProvider.getAuthServiceBy(SocialType.KAKAO);

        // then
        assertThat(authUseCase).isEqualTo(kakaoAuthService);
    }
}