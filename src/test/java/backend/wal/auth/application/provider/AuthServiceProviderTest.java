package backend.wal.auth.application.provider;

import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.service.AppleAuthService;
import backend.wal.auth.application.service.KakaoAuthService;
import backend.wal.user.domain.aggregate.SocialType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

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

    private static AuthUseCase authUseCase;

    @DisplayName("소셜 타입을 받아 소셜 타입에 해당하는 AuthService 를 가져온다")
    @ParameterizedTest
    @MethodSource("provideSocialTypeAndAuthService")
    void getAuthServiceByApple(SocialType socialType, AuthUseCase expect) {
        // given
        if (socialType.equals(APPLE)) {
            expect = appleAuthServiceMock;
        }
        else if (socialType.equals(KAKAO)) {
            expect = kakaoAuthServiceMock;
        }

        // when
        AuthUseCase actual = authServiceProvider.getAuthServiceBy(socialType);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideSocialTypeAndAuthService() {
        return Stream.of(
                Arguments.of(APPLE, authUseCase),
                Arguments.of(KAKAO, authUseCase)
        );
    }
}