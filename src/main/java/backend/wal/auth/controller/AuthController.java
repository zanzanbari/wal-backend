package backend.wal.auth.adapter.input.web;

import backend.wal.auth.adapter.input.web.dto.LoginRequest;
import backend.wal.auth.app.dto.response.TokenResponse;
import backend.wal.auth.app.service.AuthService;
import backend.wal.auth.app.service.JwtTokenService;
import backend.wal.auth.app.service.provider.AuthServiceProvider;
import backend.wal.auth.support.ExtractValidRefreshToken;
import backend.wal.utils.HttpHeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceProvider authServiceProvider;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/v2/auth/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request) {
        AuthService authService = authServiceProvider.getAuthServiceBy(request.getSocialType());
        Long userId = authService.login(request.toAuthServiceDto());
        TokenResponse tokenResponse = jwtTokenService.issueToken(userId);

        return ResponseEntity.ok()
                .header(HttpHeaderUtils.AUTHORIZATION, HttpHeaderUtils.withBearerToken(tokenResponse.getAccessToken()))
                .header(HttpHeaderUtils.REFRESH_TOKEN, tokenResponse.getRefreshToken())
                .build();
    }

    @GetMapping("/v2/auth/reissue")
    public ResponseEntity<Void> reissue(@ExtractValidRefreshToken String refreshToken) {
        String reissuedAccessToken = jwtTokenService.reissueToken(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaderUtils.AUTHORIZATION, HttpHeaderUtils.withBearerToken(reissuedAccessToken))
                .build();
    }
}
