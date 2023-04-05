package backend.wal.auth.web.support.interceptor;

import backend.wal.auth.adapter.jwt.JwtManagerAdapter;
import backend.wal.support.utils.HttpHeaderUtils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

@Component
public final class ReissueTokenInterceptor extends TokenInterceptor {

    private final JwtManagerAdapter jwtManagerAdapter;

    public ReissueTokenInterceptor(final JwtManagerAdapter jwtManagerAdapter) {
        this.jwtManagerAdapter = jwtManagerAdapter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String refreshToken = Objects.requireNonNull(request).getHeader(HttpHeaderUtils.REFRESH_TOKEN);
        checkHeaderHasToken(refreshToken);
        jwtManagerAdapter.validateToken(refreshToken);
        request.setAttribute("Refresh-Token", refreshToken);
        return true;
    }
}
