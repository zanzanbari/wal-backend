package backend.wal.auth.controller;

import backend.wal.auth.controller.dto.LoginRequest;
import backend.wal.auth.app.dto.response.TokenResponse;
import backend.wal.auth.app.service.AuthService;
import backend.wal.auth.app.service.JwtTokenService;
import backend.wal.auth.app.service.provider.AuthServiceProvider;
import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.ExtractValidRefreshToken;
import backend.wal.utils.HttpHeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/auth")
public class AuthController {

    private final AuthServiceProvider authServiceProvider;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request) {
        AuthService authService = authServiceProvider.getAuthServiceBy(request.getSocialType());
        Long userId = authService.login(request.toAuthServiceDto());
        TokenResponse tokenResponse = jwtTokenService.issueToken(userId);

        return ResponseEntity.ok()
                .header(HttpHeaderUtils.AUTHORIZATION, HttpHeaderUtils.withBearerToken(tokenResponse.getAccessToken()))
                .header(HttpHeaderUtils.REFRESH_TOKEN, tokenResponse.getRefreshToken())
                .build();
    }

    @Authentication
    @GetMapping("/reissue")
    public ResponseEntity<Void> reissue(@ExtractValidRefreshToken String refreshToken) {
        String reissuedAccessToken = jwtTokenService.reissueToken(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaderUtils.AUTHORIZATION, HttpHeaderUtils.withBearerToken(reissuedAccessToken))
                .build();
    }
}
