package backend.wal.auth.support.resolver;

import backend.wal.auth.exception.InternalAuthServerException;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import backend.wal.auth.support.ExtractValidRefreshToken;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RefreshTokenResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ExtractValidRefreshToken.class)
                && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String validRefreshToken = (String) webRequest.getAttribute("Refresh-Token", 0);
        checkGetRefreshToken(validRefreshToken, parameter);
        return validRefreshToken;
    }

    private static void checkGetRefreshToken(String refreshToken, MethodParameter parameter) {
        if (refreshToken == null) {
            throw InternalAuthServerException.attributeNotFound(parameter);
        }
    }
}
