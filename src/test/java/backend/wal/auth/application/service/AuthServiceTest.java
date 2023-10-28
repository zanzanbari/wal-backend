package backend.wal.auth.application.service;

import backend.wal.auth.application.port.in.LoginRequestDto;
import backend.wal.auth.application.port.in.LoginResponseDto;
import backend.wal.auth.application.port.out.OAuthApiClientPort;
import backend.wal.auth.application.port.out.OAuthUserInfoResponseDto;
import backend.wal.auth.domain.service.OAuthDomainService;
import backend.wal.support.Role;
import backend.wal.user.domain.aggregate.SocialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private static final String SOCIAL_ACCESS_TOKEN = "socialAccessToken";
    private static final String FCM_TOKEN = "FcmToken";
    private static final String SOCIAL_ID = "socialId";
    private static final String NICKNAME = "nickname";
    private static final String USER_ROLE = Role.USER.name();

    @Mock
    private OAuthApiClientPort oAuthApiClientPort;

    @Mock
    private OAuthDomainService oAuthDomainService;

    @InjectMocks
    private KakaoAuthService kakaoAuthService;

    @InjectMocks
    private AppleAuthService appleAuthService;

    private Long userId;
    private LoginRequestDto loginRequestDto;
    private OAuthUserInfoResponseDto oAuthUserInfoResponseDto;
    private LoginResponseDto loginResponseDto;

    @BeforeEach
    void setUp() {
        userId = 1L;
        oAuthUserInfoResponseDto = new OAuthUserInfoResponseDto(SOCIAL_ID, NICKNAME);
        loginResponseDto = new LoginResponseDto(userId, "nickname", USER_ROLE, false);
    }

    @DisplayName("카카오 로그인을 수행하면 LoginResponseDto 를 반환한다")
    @Test
    void kakaoLogin() {
        // given
        loginRequestDto = new LoginRequestDto(SOCIAL_ACCESS_TOKEN, SocialType.KAKAO, FCM_TOKEN);
        when(oAuthApiClientPort.getOAuthUserId(SOCIAL_ACCESS_TOKEN))
                .thenReturn(oAuthUserInfoResponseDto);
        when(oAuthDomainService.signupOrLogin(loginRequestDto, oAuthUserInfoResponseDto))
                .thenReturn(loginResponseDto);

        // when
        LoginResponseDto actual = kakaoAuthService.login(loginRequestDto);

        // then
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.isNewUser()).isFalse();
    }

    @DisplayName("애플 로그인을 수행하면 LoginResponseDto 를 반환한다")
    @Test
    void appleLogin() {
        // given
        loginRequestDto = new LoginRequestDto(SOCIAL_ACCESS_TOKEN, SocialType.APPLE, FCM_TOKEN);
        when(oAuthApiClientPort.getOAuthUserId(SOCIAL_ACCESS_TOKEN))
                .thenReturn(oAuthUserInfoResponseDto);
        when(oAuthDomainService.signupOrLogin(loginRequestDto, oAuthUserInfoResponseDto))
                .thenReturn(loginResponseDto);

        // when
        LoginResponseDto actual = appleAuthService.login(loginRequestDto);

        // then
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.isNewUser()).isFalse();
    }
}