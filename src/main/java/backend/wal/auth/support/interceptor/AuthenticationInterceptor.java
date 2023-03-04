package backend.wal.auth.support.interceptor;

import backend.wal.auth.exception.InternalAuthServerException;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.token.JwtManager;
import backend.wal.utils.HttpHeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor extends TokenInterceptor implements HandlerInterceptor {

    private final JwtManager jwtManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Authentication authentication = handlerMethod.getMethodAnnotation(Authentication.class);
        checkHasAnnotation(authentication);

        String bearerTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        checkHeaderHasToken(bearerTokenHeader);
        checkIsBearerType(bearerTokenHeader);

        String accessToken = bearerTokenHeader.substring(HttpHeaderUtils.AUTHENTICATION_TYPE.length());
        jwtManager.validateToken(accessToken);

        Long loginUserId = jwtManager.getLoginUserIdFromAccessToken(accessToken);
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
