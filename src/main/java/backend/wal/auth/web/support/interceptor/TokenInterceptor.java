package backend.wal.auth.web.support.interceptor;

import backend.wal.auth.exception.UnAuthorizedTokenException;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public abstract class TokenInterceptor implements HandlerInterceptor {

    void checkHeaderHasToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw UnAuthorizedTokenException.tokenNotFound(token);
        }
    }
}
