package backend.wal.auth.app.service.provider;

import backend.wal.auth.app.service.AppleAuthService;
import backend.wal.auth.app.service.AuthService;
import backend.wal.auth.app.service.KakaoAuthService;
import backend.wal.user.domain.aggregate.vo.SocialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceProviderTest {

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @DisplayName("socialType 을 받아, 해당 타입과 일치하는 AuthService 를 반환한다")
    @Test
    void getAuthServiceBy() {
        // given
        SocialType appleType = SocialType.APPLE;
        SocialType kakaoType = SocialType.KAKAO;

        // when
        AuthService appleAuthService = authServiceProvider.getAuthServiceBy(appleType);
        AuthService kakaoAuthService = authServiceProvider.getAuthServiceBy(kakaoType);

        // then
        assertAll(
                () -> assertTrue(appleAuthService instanceof AppleAuthService),
                () -> assertTrue(kakaoAuthService instanceof KakaoAuthService)
        );
    }
}