package backend.wal.auth.application.service;

import backend.wal.auth.application.port.in.LoginRequestDto;
import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.port.out.OAuthUserInfoResponseDto;
import backend.wal.auth.application.port.out.OAuthApiClientPort;
import backend.wal.auth.application.port.out.RegisterFcmPort;
import backend.wal.auth.application.port.out.UserPort;
import backend.wal.auth.exception.UnAuthorizedUserException;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class KakaoAuthService implements AuthUseCase {

    private final OAuthApiClientPort oAuthApiClientPort;
    private final UserPort userPort;
    private final RegisterFcmPort registerFcmPort;

    public KakaoAuthService(final OAuthApiClientPort oAuthApiClientPort, final UserPort userPort,
                            final RegisterFcmPort registerFcmPort) {
        this.oAuthApiClientPort = oAuthApiClientPort;
        this.userPort = userPort;
        this.registerFcmPort = registerFcmPort;
    }

    @Override
    @Transactional
    public Long login(LoginRequestDto requestDto) {
        OAuthUserInfoResponseDto oAuthUserInfo = oAuthApiClientPort.getOAuthUserId(requestDto.getSocialAccessToken());
        User alreadyUser = userPort.findSocialUserCall(oAuthUserInfo.getId(), requestDto.getSocialType());
        if (alreadyUser == null) {
            Long newUserId = userPort.signupCall(requestDto.toCreateUserDto(oAuthUserInfo.getNickname(), oAuthUserInfo.getId()));
            registerFcmPort.registerCall(requestDto.toFcmTokenServiceDto(newUserId));
            return newUserId;
        }
        if (alreadyUser.isDeleted()) {
            throw UnAuthorizedUserException.resignUser();
        }
        return alreadyUser.getId();
    }
}
