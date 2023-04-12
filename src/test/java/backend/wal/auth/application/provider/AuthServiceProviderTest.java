package backend.wal.auth.application.provider;

import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.service.AppleAuthService;
import backend.wal.auth.application.service.KakaoAuthService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static backend.wal.user.domain.aggregate.SocialType.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthServiceProviderTest {

    @Autowired
    private AppleAuthService appleAuthServiceMock;

    @Autowired
    private KakaoAuthService kakaoAuthServiceMock;

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @DisplayName("소셜 타입을 받아 소셜 타입에 해당하는 AuthService 를 가져온다")
    @Test
    void getAuthServiceByApple() {
        // when
        AuthUseCase actual = authServiceProvider.getAuthServiceBy(APPLE);

        // then
        assertThat(actual).isEqualTo(appleAuthServiceMock);
    }

    @DisplayName("소셜 타입을 받아 소셜 타입에 해당하는 AuthService 를 가져온다")
    @Test
    void getAuthServiceByKakao() {
        // when
        AuthUseCase actual = authServiceProvider.getAuthServiceBy(KAKAO);

        // then
        assertThat(actual).isEqualTo(kakaoAuthServiceMock);
    }
}