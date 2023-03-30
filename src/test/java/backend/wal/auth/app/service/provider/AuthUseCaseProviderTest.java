package backend.wal.auth.app.service.provider;

import backend.wal.auth.application.service.AppleAuthService;
import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.service.KakaoAuthService;
import backend.wal.auth.application.provider.AuthServiceProvider;
import backend.wal.user.domain.aggregate.SocialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthUseCaseProviderTest {

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @DisplayName("socialType 을 받아, 해당 타입과 일치하는 AuthService 를 반환한다")
    @Test
    void getAuthServiceBy() {
        // given
        SocialType appleType = SocialType.APPLE;
        SocialType kakaoType = SocialType.KAKAO;

        // when
        AuthUseCase appleAuthUseCase = authServiceProvider.getAuthServiceBy(appleType);
        AuthUseCase kakaoAuthUseCase = authServiceProvider.getAuthServiceBy(kakaoType);

        // then
        assertAll(
                () -> assertTrue(appleAuthUseCase instanceof AppleAuthService),
                () -> assertTrue(kakaoAuthUseCase instanceof KakaoAuthService)
        );
    }
}