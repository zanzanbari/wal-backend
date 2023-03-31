package backend.wal.auth.application.provider;

import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.service.AppleAuthService;
import backend.wal.auth.application.service.KakaoAuthService;
import backend.wal.user.domain.aggregate.SocialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static backend.wal.user.domain.aggregate.SocialType.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthServiceProviderTest {

    @Mock
    private AppleAuthService appleAuthServiceMock;

    @Mock
    private KakaoAuthService kakaoAuthServiceMock;

    @InjectMocks
    private AuthServiceProvider authServiceProvider;

    private static AppleAuthService appleAuthService;
    private static KakaoAuthService kakaoAuthService;

    @DisplayName("소셜 타입을 받아 소셜 타입에 해당하는 AuthService 를 가져온다")
    @ParameterizedTest
    @MethodSource("provideSocialTypeAndAuthService")
    void getAuthServiceByApple(SocialType socialType, AuthUseCase expect) {
        // given
        appleAuthService = appleAuthServiceMock;
        kakaoAuthService = kakaoAuthServiceMock;

        // when
        AuthUseCase actual = authServiceProvider.getAuthServiceBy(socialType);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideSocialTypeAndAuthService() {
        return Stream.of(
                Arguments.of(APPLE, appleAuthService),
                Arguments.of(KAKAO, kakaoAuthService)
        );
    }
}