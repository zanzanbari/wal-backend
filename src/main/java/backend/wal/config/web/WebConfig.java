package backend.wal.config.web;

import backend.wal.auth.web.support.interceptor.AuthenticationInterceptor;
import backend.wal.auth.web.support.interceptor.ReissueTokenInterceptor;
import backend.wal.auth.web.support.resolver.LoginUserResolver;
import backend.wal.auth.web.support.resolver.RefreshTokenResolver;
import backend.wal.support.query.JPAQueryManageInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final ReissueTokenInterceptor reissueTokenInterceptor;
    private final JPAQueryManageInterceptor jpaQueryManageInterceptor;
    private final LoginUserResolver loginUserResolver;
    private final RefreshTokenResolver refreshTokenResolver;

    public WebConfig(final AuthenticationInterceptor authenticationInterceptor,
                     final ReissueTokenInterceptor reissueTokenInterceptor,
                     final @Autowired(required = false) JPAQueryManageInterceptor jpaQueryManageInterceptor,
                     final LoginUserResolver loginUserResolver,
                     final RefreshTokenResolver refreshTokenResolver) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.reissueTokenInterceptor = reissueTokenInterceptor;
        this.jpaQueryManageInterceptor = jpaQueryManageInterceptor;
        this.loginUserResolver = loginUserResolver;
        this.refreshTokenResolver = refreshTokenResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/auth/**")
                .excludePathPatterns("/v2/auth/login")
                .excludePathPatterns("/v2/auth/reissue")
                .excludePathPatterns("/health")
                .excludePathPatterns("/profile");
        registry.addInterceptor(reissueTokenInterceptor)
                .addPathPatterns("/v2/auth/reissue");
        if (jpaQueryManageInterceptor != null) {
            registry.addInterceptor(jpaQueryManageInterceptor)
                    .addPathPatterns("/**");
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserResolver);
        resolvers.add(refreshTokenResolver);
    }
}
