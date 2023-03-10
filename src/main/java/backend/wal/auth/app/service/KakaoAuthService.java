package backend.wal.auth.app.service;

import backend.wal.auth.app.dto.request.LoginRequestDto;
import backend.wal.auth.infrastructure.kakao.KakaoApiClient;
import backend.wal.auth.infrastructure.kakao.dto.KakaoUserInfoResponse;
import backend.wal.notification.service.FcmTokenService;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.user.app.service.UserService;
import backend.wal.utils.HttpHeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService implements AuthService {

    private final KakaoApiClient kakaoApiClient;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FcmTokenService fcmTokenService;

    @Override
    @Transactional
    public Long login(LoginRequestDto requestDto) {
        String bearerAccessToken = HttpHeaderUtils.withBearerToken(requestDto.getSocialAccessToken());
        KakaoUserInfoResponse kakaoUserInfo = kakaoApiClient.getKakaoUserInfo(bearerAccessToken);
        String socialId = kakaoUserInfo.getId();
        User alreadyUser = userRepository.findUserBySocialInfoSocialIdAndSocialInfoSocialType(socialId, requestDto.getSocialType());
        if (alreadyUser == null) {
            Long newUserId = userService.signup(requestDto.toCreateUserDto(kakaoUserInfo.getNickname(), socialId));
            fcmTokenService.register(requestDto.toFcmTokenServiceDto(newUserId));
            return newUserId;
        }
        return alreadyUser.getId();
    }
}
