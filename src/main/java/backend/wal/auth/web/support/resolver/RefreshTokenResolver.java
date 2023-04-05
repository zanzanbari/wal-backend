package backend.wal.auth.web.support.resolver;

import backend.wal.auth.exception.InternalAuthServerException;
import backend.wal.support.annotation.ExtractValidRefreshToken;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public final class RefreshTokenResolver implements HandlerMethodArgumentResolver {

    private static final String REFRESH_TOKEN_ATTRIBUTE = "Refresh-Token";
    private static final int SCOPE = 0;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ExtractValidRefreshToken.class)
                && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String validRefreshToken = (String) webRequest.getAttribute(REFRESH_TOKEN_ATTRIBUTE, SCOPE);
        checkGetRefreshToken(validRefreshToken, parameter);
        return validRefreshToken;
    }

    private static void checkGetRefreshToken(String refreshToken, MethodParameter parameter) {
        if (refreshToken == null) {
            throw InternalAuthServerException.attributeNotFound(parameter.getClass(), parameter.getMethod());
        }
    }
}
