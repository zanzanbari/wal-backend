package backend.wal.auth.app.service;

import backend.wal.auth.application.port.in.LoginRequestDto;
import backend.wal.auth.application.service.KakaoAuthService;
import backend.wal.auth.adapter.oauth.kakao.KakaoApiClientCaller;
import backend.wal.auth.adapter.oauth.kakao.dto.KakaoAccount;
import backend.wal.auth.adapter.oauth.kakao.dto.KakaoUserInfoResponse;
import backend.wal.auth.adapter.oauth.kakao.dto.Profile;
import backend.wal.notification.application.service.RegisterFcmTokenService;
import backend.wal.user.application.service.ChangeUserInfoService;
import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KakaoAuthServiceTest {

    private static final String SOCIAL_ACCESS_TOKEN = "socialAccessToken";
    private static final SocialType SOCIAL_TYPE = SocialType.KAKAO;
    private static final String FCM_TOKEN = "FcmToken";
    private static final String SOCIAL_ID = "socialId";
    private static final String NICKNAME = "nickname";

    @Mock
    private KakaoApiClientCaller kakaoApiClientCaller;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChangeUserInfoService userService;

    @Mock
    private RegisterFcmTokenService registerFcmTokenService;

    @InjectMocks
    private KakaoAuthService kakaoAuthService;

    @DisplayName("이미 존재하는 회원이 login 을 하면 기존 회원의 userId 를 반환한다")
    @Test
    void alreadyUserLogin() {
        // given
        LoginRequestDto requestDto = new LoginRequestDto(SOCIAL_ACCESS_TOKEN, SOCIAL_TYPE, FCM_TOKEN);
        KakaoUserInfoResponse kakaoResponse = new KakaoUserInfoResponse(SOCIAL_ID, new KakaoAccount(new Profile(NICKNAME)));
        User alreadyUser = User.createGeneral(requestDto.toCreateUserDto(kakaoResponse.getNickname(), kakaoResponse.getId()));
        when(kakaoApiClientCaller.getKakaoUserInfo("Bearer " + SOCIAL_ACCESS_TOKEN))
                .thenReturn(kakaoResponse);
        when(userRepository.findUserBySocialInfoSocialIdAndSocialInfoSocialType(kakaoResponse.getId(), SOCIAL_TYPE))
                .thenReturn(alreadyUser);

        // when
        Long userId = kakaoAuthService.login(requestDto);

        // then
        assertThat(userId).isEqualTo(alreadyUser.getId());
    }

    @DisplayName("존재하지 않는 회원이 login 을 하면 회원 가입을 한 후 회원 가입한 유저의 userId 를 반환한다")
    @Test
    void newUserSignupAndLogin() {
        // given
        Long newUserId = 2L;
        LoginRequestDto requestDto = new LoginRequestDto(SOCIAL_ACCESS_TOKEN, SOCIAL_TYPE, FCM_TOKEN);
        KakaoUserInfoResponse kakaoResponse = new KakaoUserInfoResponse(SOCIAL_ID, new KakaoAccount(new Profile(NICKNAME)));
        when(kakaoApiClientCaller.getKakaoUserInfo("Bearer " + SOCIAL_ACCESS_TOKEN))
                .thenReturn(kakaoResponse);
        when(userRepository.findUserBySocialInfoSocialIdAndSocialInfoSocialType(kakaoResponse.getId(), SOCIAL_TYPE))
                .thenReturn(null);
        when(userService.signup(any()))
                .thenReturn(newUserId);

        // when
        Long userId = kakaoAuthService.login(requestDto);

        // then
        assertThat(userId).isEqualTo(newUserId);
    }
}