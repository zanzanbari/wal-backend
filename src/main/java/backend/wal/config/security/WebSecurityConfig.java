package backend.wal.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll() // 토큰을 활용하는 경우 모든 요청에 대해 접근 가능하도록 설정
                .and()
                .httpBasic().and()
                .formLogin().disable() // form 기반 로그인 비활성화
                .logout().disable()
                .csrf().disable()
                // session 사용 안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .frameOptions().sameOrigin()
                .cacheControl().and()
                .xssProtection().and()
                .httpStrictTransportSecurity().disable();
        return http.build();
    }
}
