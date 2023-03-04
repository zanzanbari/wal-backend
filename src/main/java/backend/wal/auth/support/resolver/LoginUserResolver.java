package backend.wal.auth.support.resolver;

import backend.wal.auth.exception.InternalAuthServerException;
import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        checkHasAuthAnnotation(parameter);
        Long validUserId = (Long) webRequest.getAttribute("USER_ID", 0);
        checkGetLoginUserId(validUserId, parameter);
        return validUserId;
    }

    private static void checkHasAuthAnnotation(MethodParameter parameter) {
        if (parameter.getMethodAnnotation(Authentication.class) == null) {
            throw InternalAuthServerException.annotationNotFound();
        }
    }

    private static void checkGetLoginUserId(Long validUserId, MethodParameter parameter) {
        if (validUserId == null) {
            throw InternalAuthServerException.attributeNotFound(parameter);
        }
    }
}
