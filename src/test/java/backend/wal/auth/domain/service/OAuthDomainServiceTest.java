package backend.wal.auth.domain.service;

import backend.wal.auth.application.port.in.LoginRequestDto;
import backend.wal.auth.application.port.in.LoginResponseDto;
import backend.wal.auth.application.port.out.OAuthUserInfoResponseDto;
import backend.wal.auth.application.port.out.FcmTokenPort;
import backend.wal.auth.application.port.out.UserPort;
import backend.wal.auth.exception.ForbiddenUserException;
import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuthDomainServiceTest {

    private static final String SOCIAL_ACCESS_TOKEN = "socialAccessToken";
    private static final SocialType SOCIAL_TYPE = SocialType.KAKAO;
    private static final String FCM_TOKEN = "FcmToken";
    private static final String SOCIAL_ID = "socialId";
    private static final String NICKNAME = "nickname";

    @Mock
    private UserPort userPort;

    @Mock
    private FcmTokenPort fcmTokenPort;

    @InjectMocks
    private OAuthDomainService oAuthDomainService;

    private LoginRequestDto loginRequestDto;
    private OAuthUserInfoResponseDto oAuthUserInfoResponseDto;

    @BeforeEach
    void setUp() {
        loginRequestDto = new LoginRequestDto(SOCIAL_ACCESS_TOKEN, SOCIAL_TYPE, FCM_TOKEN);
        oAuthUserInfoResponseDto = new OAuthUserInfoResponseDto(SOCIAL_ID, NICKNAME);
    }

    @DisplayName("이미 존재하는 회원이 login 을 하면 기존 회원의 userId 와 새로운 회원 여부 false 를 반환한다")
    @Test
    void signupOrLogin() {
        // given
        User alreadyUser = mock(User.class);
        Long userId = 1L;
        when(userPort.findSocialUserCall(any(String.class), any(SocialType.class)))
                .thenReturn(alreadyUser);
        when(alreadyUser.getId())
                .thenReturn(userId);

        // when
        LoginResponseDto loginResponseDto = oAuthDomainService.signupOrLogin(loginRequestDto, oAuthUserInfoResponseDto);

        // then
        assertAll(
                () -> assertThat(loginResponseDto.getUserId()).isEqualTo(userId),
                () -> assertThat(loginResponseDto.isNewUser()).isFalse()
        );
    }

    @DisplayName("존재하지 않는 회원이 login 을 하면 회원 가입을 한 후 회원 가입한 유저의 userId 와 새로운 회원 여부 true 를 반환한다")
    @Test
    void newUserSignupAndLogin() {
        // given
        Long newUserId = 2L;
        when(userPort.findSocialUserCall(eq(oAuthUserInfoResponseDto.getId()), any(SocialType.class)))
                .thenReturn(null);
        when(userPort.signupCall(any()))
                .thenReturn(newUserId);

        // when
        LoginResponseDto loginResponseDto = oAuthDomainService.signupOrLogin(loginRequestDto, oAuthUserInfoResponseDto);

        // then
        assertThat(loginResponseDto.getUserId()).isEqualTo(newUserId);
        assertThat(loginResponseDto.isNewUser()).isTrue();
    }

    @DisplayName("탈퇴한 회원이 24시간 이내에 login 을 하면 에러가 발생한다")
    @Test
    void resignUserLogin() {
        // given
        User alreadyUser = User.createGeneral(
                loginRequestDto.toCreateUserDto(
                        oAuthUserInfoResponseDto.getNickname(),
                        oAuthUserInfoResponseDto.getId())
        );
        alreadyUser.resign();
        when(userPort.findSocialUserCall(eq(oAuthUserInfoResponseDto.getId()), any(SocialType.class)))
                .thenReturn(alreadyUser);

        // when, then
        assertThatThrownBy(() -> oAuthDomainService.signupOrLogin(loginRequestDto, oAuthUserInfoResponseDto))
                .isInstanceOf(ForbiddenUserException.class)
                .hasMessage("탈퇴한지 24시간이 지나지 않아 재가입 할 수 없습니다");
    }
}