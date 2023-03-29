package backend.wal.auth.application.service;

import backend.wal.auth.application.port.AuthUseCase;
import backend.wal.auth.application.port.OAuthApiClientPort;
import backend.wal.auth.application.port.dto.LoginRequestDto;
import backend.wal.auth.application.port.dto.OAuthUserInfoResponseDto;
import backend.wal.auth.exception.UnAuthorizedUserException;
import backend.wal.notification.application.service.RegisterFcmTokenService;
import backend.wal.user.application.port.FindSocialUserUseCase;
import backend.wal.user.application.port.RegisterUserUseCase;
import backend.wal.user.domain.aggregate.entity.User;
import lombok.RequiredArgsConstructor;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

@AppService
@RequiredArgsConstructor
public class KakaoAuthService implements AuthUseCase {

    private final OAuthApiClientPort oAuthApiClientPort;
    private final FindSocialUserUseCase findSocialUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final RegisterFcmTokenService registerFcmTokenService;

    @Override
    @Transactional
    public Long login(LoginRequestDto requestDto) {
        OAuthUserInfoResponseDto oAuthUserInfo = oAuthApiClientPort.getOAuthUserId(requestDto.getSocialAccessToken());
        User alreadyUser = findSocialUserUseCase.findUserBySocialIdAndSocialType(oAuthUserInfo.getId(), requestDto.getSocialType());
        if (alreadyUser == null) {
            Long newUserId = registerUserUseCase.signup(requestDto.toCreateUserDto(oAuthUserInfo.getNickname(), oAuthUserInfo.getId()));
            registerFcmTokenService.register(requestDto.toFcmTokenServiceDto(newUserId));
            return newUserId;
        }
        if (alreadyUser.isDeleted()) {
            throw UnAuthorizedUserException.resignUser();
        }
        return alreadyUser.getId();
    }
}
