package backend.wal.auth.support.interceptor;

import backend.wal.auth.exception.UnAuthorizedTokenException;
import org.springframework.util.StringUtils;

public abstract class TokenInterceptor {

    protected static void checkHeaderHasToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw UnAuthorizedTokenException.tokenNotFound(token);
        }
    }
}
