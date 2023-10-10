package backend.wal.auth.web.support.resolver;

import backend.wal.auth.exception.InternalAuthServerException;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public final class LoginUserResolver implements HandlerMethodArgumentResolver {

    private static final String LOGIN_USER_ATTRIBUTE = "USER_ID";
    private static final int SCOPE = 0;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        checkHasAuthAnnotation(parameter);
        Long validUserId = (Long) webRequest.getAttribute(LOGIN_USER_ATTRIBUTE, SCOPE);
        checkGetLoginUserId(validUserId, parameter);
        return validUserId;
    }

    private void checkHasAuthAnnotation(MethodParameter parameter) {
        if (parameter.getMethodAnnotation(Authentication.class) == null) {
            throw InternalAuthServerException.annotationNotFound();
        }
    }

    private void checkGetLoginUserId(Long validUserId, MethodParameter parameter) {
        if (validUserId == null) {
            throw InternalAuthServerException.attributeNotFound(
                    LOGIN_USER_ATTRIBUTE, parameter.getClass(), parameter.getMethod());
        }
    }
}
