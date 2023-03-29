package backend.wal.auth.web.support.interceptor;

import backend.wal.auth.adapter.jwt.JwtManagerAdapter;
import backend.wal.auth.exception.InternalAuthServerException;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import backend.wal.support.annotation.Authentication;
import backend.wal.utils.HttpHeaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class AuthenticationInterceptor extends TokenInterceptor {

    private final JwtManagerAdapter jwtManagerAdapter;

    public AuthenticationInterceptor(final JwtManagerAdapter jwtManagerAdapter) {
        this.jwtManagerAdapter = jwtManagerAdapter;
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
        jwtManagerAdapter.validateToken(accessToken);

        Long loginUserId = jwtManagerAdapter.getLoginUserIdFromAccessToken(accessToken);
        request.setAttribute("USER_ID", loginUserId);

        return true;
    }

    private static void checkHasAnnotation(Authentication authentication) {
        if (authentication == null) {
            throw InternalAuthServerException.annotationNotFound();
        }
    }

    private static void checkIsBearerType(String bearerTokenHeader) {
        if (!bearerTokenHeader.startsWith(HttpHeaderUtils.AUTHENTICATION_TYPE)) {
            throw UnAuthorizedTokenException.authenticationTypeNotFound();
        }
    }
}
