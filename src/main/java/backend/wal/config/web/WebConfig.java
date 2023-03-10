package backend.wal.config.web;

import backend.wal.auth.support.interceptor.AuthenticationInterceptor;
import backend.wal.auth.support.interceptor.ReissueTokenInterceptor;
import backend.wal.auth.support.resolver.LoginUserResolver;
import backend.wal.auth.support.resolver.RefreshTokenResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final ReissueTokenInterceptor reissueTokenInterceptor;
    private final LoginUserResolver loginUserResolver;
    private final RefreshTokenResolver refreshTokenResolver;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/v2/auth/login")
                .excludePathPatterns("/v2/auth/reissue");
        registry.addInterceptor(reissueTokenInterceptor)
                .addPathPatterns("/v2/auth/reissue");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserResolver);
        resolvers.add(refreshTokenResolver);
    }
}
