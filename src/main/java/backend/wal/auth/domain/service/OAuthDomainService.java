package backend.wal.auth.domain.service;

import backend.wal.auth.application.port.in.LoginRequestDto;
import backend.wal.auth.application.port.in.LoginResponseDto;
import backend.wal.auth.application.port.out.OAuthUserInfoResponseDto;
import backend.wal.auth.application.port.out.FcmTokenPort;
import backend.wal.auth.application.port.out.UserPort;
import backend.wal.auth.exception.ForbiddenUserException;
import backend.wal.support.annotation.DomainService;
import backend.wal.user.domain.aggregate.entity.User;

@DomainService
public class OAuthDomainService {

    private final UserPort userPort;
    private final FcmTokenPort fcmTokenPort;

    public OAuthDomainService(final UserPort userPort, final FcmTokenPort fcmTokenPort) {
        this.userPort = userPort;
        this.fcmTokenPort = fcmTokenPort;
    }

    public LoginResponseDto signupOrLogin(LoginRequestDto requestDto, OAuthUserInfoResponseDto oAuthUserInfo) {
        User alreadyUser = userPort.findSocialUserCall(oAuthUserInfo.getId(), requestDto.getSocialType());
        if (alreadyUser == null) {
            Long newUserId = userPort.signupCall(requestDto.toCreateUserDto(oAuthUserInfo.getNickname(), oAuthUserInfo.getId()));
            fcmTokenPort.registerCall(requestDto.toFcmTokenServiceDto(newUserId));
            return new LoginResponseDto(newUserId, true);
        }
        if (alreadyUser.isDeleted()) {
            throw ForbiddenUserException.resignUser();
        }
        fcmTokenPort.checkAndUpdateCall(requestDto.toUpdateTokenServiceDto(alreadyUser.getId()));
        return new LoginResponseDto(alreadyUser.getId(), false);
    }
}
