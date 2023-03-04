package backend.wal.auth.support.interceptor;

import backend.wal.auth.support.token.JwtManager;
import backend.wal.utils.HttpHeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReissueTokenInterceptor extends TokenInterceptor implements HandlerInterceptor {

    private final JwtManager jwtManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String refreshToken = Objects.requireNonNull(request).getHeader(HttpHeaderUtils.REFRESH_TOKEN);
        checkHeaderHasToken(refreshToken);
        jwtManager.validateToken(refreshToken);
        request.setAttribute("Refresh-Token", refreshToken);
        return true;
    }
}
