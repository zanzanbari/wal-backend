package backend.wal.auth.domain.service;

import backend.wal.auth.application.port.in.LoginRequestDto;
import backend.wal.auth.application.port.in.LoginResponseDto;
import backend.wal.auth.application.port.out.OAuthUserInfoResponseDto;
import backend.wal.auth.application.port.out.RegisterFcmPort;
import backend.wal.auth.application.port.out.UserPort;
import backend.wal.auth.exception.ForbiddenUserException;
import backend.wal.support.annotation.DomainService;
import backend.wal.user.domain.aggregate.entity.User;

@DomainService
public class OAuthDomainService {

    private final UserPort userPort;
    private final RegisterFcmPort registerFcmPort;

    public OAuthDomainService(final UserPort userPort, final RegisterFcmPort registerFcmPort) {
        this.userPort = userPort;
        this.registerFcmPort = registerFcmPort;
    }

    public LoginResponseDto signupOrLogin(LoginRequestDto requestDto, OAuthUserInfoResponseDto oAuthUserInfo) {
        User alreadyUser = userPort.findSocialUserCall(oAuthUserInfo.getId(), requestDto.getSocialType());
        if (alreadyUser == null) {
            Long newUserId = userPort.signupCall(requestDto.toCreateUserDto(oAuthUserInfo.getNickname(), oAuthUserInfo.getId()));
            registerFcmPort.registerCall(requestDto.toFcmTokenServiceDto(newUserId));
            return new LoginResponseDto(newUserId, true);
        }
        if (alreadyUser.isDeleted()) {
            throw ForbiddenUserException.resignUser();
        }
        return new LoginResponseDto(alreadyUser.getId(), false);
    }
}
