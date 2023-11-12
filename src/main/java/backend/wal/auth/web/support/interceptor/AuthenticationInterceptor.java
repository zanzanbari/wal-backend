package backend.wal.auth.web.support.interceptor;

import backend.wal.auth.application.port.out.JwtManagerPort;
import backend.wal.auth.application.port.out.JwtPayloadInfo;
import backend.wal.auth.exception.InternalAuthServerException;
import backend.wal.auth.exception.UnAuthorizedRoleException;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.utils.HttpHeaderUtils;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class AuthenticationInterceptor extends TokenInterceptor {

    private final JwtManagerPort jwtManagerPort;

    public AuthenticationInterceptor(final JwtManagerPort jwtManagerPort) {
        this.jwtManagerPort = jwtManagerPort;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Authentication authentication = handlerMethod.getMethodAnnotation(Authentication.class);
        checkHasAnnotation(authentication);

        String bearerTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        checkHeaderHasToken(bearerTokenHeader);
        checkIsBearerType(bearerTokenHeader);

        String accessToken = bearerTokenHeader.substring(HttpHeaderUtils.AUTHENTICATION_TYPE.length());
        jwtManagerPort.validateToken(accessToken);

        JwtPayloadInfo payloadInfo = jwtManagerPort.getLoginUserIdFromAccessToken(accessToken);
        checkAuthenticateRole(authentication.value().name(), payloadInfo.getRole());

        request.setAttribute("USER_ID", payloadInfo.getId());
        return true;
    }

    private void checkHasAnnotation(Authentication authentication) {
        if (authentication == null) {
            throw InternalAuthServerException.annotationNotFound();
        }
    }

    private void checkIsBearerType(String bearerTokenHeader) {
        if (!bearerTokenHeader.startsWith(HttpHeaderUtils.AUTHENTICATION_TYPE)) {
            throw UnAuthorizedTokenException.authenticationTypeNotFound();
        }
    }

    private void checkAuthenticateRole(String expectRole, String actualRole) {
        if (!expectRole.equals(actualRole)) {
            throw UnAuthorizedRoleException.wrong(expectRole);
        }
    }
}
