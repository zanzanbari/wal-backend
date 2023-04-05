package backend.wal.auth.application.service;

import backend.wal.auth.application.port.in.LoginRequestDto;
import backend.wal.auth.application.port.in.LoginResponseDto;
import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.port.out.OAuthUserInfoResponseDto;
import backend.wal.auth.application.port.out.OAuthApiClientPort;
import backend.wal.auth.domain.service.OAuthDomainService;
import backend.wal.support.annotation.AppService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class AppleAuthService implements AuthUseCase {

    private static final String APPLE_OAUTH_CLIENT_PORT = "appleApiClientAdapter";

    private final OAuthApiClientPort oAuthApiClientPort;
    private final OAuthDomainService oAuthDomainService;

    public AppleAuthService(@Qualifier(APPLE_OAUTH_CLIENT_PORT) final OAuthApiClientPort oAuthApiClientPort,
                            final OAuthDomainService oAuthDomainService) {
        this.oAuthApiClientPort = oAuthApiClientPort;
        this.oAuthDomainService = oAuthDomainService;
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        OAuthUserInfoResponseDto oAuthUserInfo = oAuthApiClientPort.getOAuthUserId(requestDto.getSocialAccessToken());
        return oAuthDomainService.signupOrLogin(requestDto, oAuthUserInfo);
    }
}
