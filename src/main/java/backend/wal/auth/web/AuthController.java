package backend.wal.auth.web;

import backend.wal.auth.application.port.dto.TokenResponse;
import backend.wal.auth.application.port.IssueTokenUseCase;
import backend.wal.auth.application.port.AuthUseCase;
import backend.wal.auth.application.provider.AuthServiceProvider;
import backend.wal.auth.web.dto.LoginRequest;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.ExtractValidRefreshToken;
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
    private final IssueTokenUseCase issueTokenUseCase;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request) {
        AuthUseCase authUseCase = authServiceProvider.getAuthServiceBy(request.getSocialType());
        Long userId = authUseCase.login(request.toAuthServiceDto());
        TokenResponse tokenResponse = issueTokenUseCase.issue(userId);

        return ResponseEntity.ok()
                .header(HttpHeaderUtils.AUTHORIZATION, HttpHeaderUtils.withBearerToken(tokenResponse.getAccessToken()))
                .header(HttpHeaderUtils.REFRESH_TOKEN, tokenResponse.getRefreshToken())
                .build();
    }

    @Authentication
    @GetMapping("/reissue")
    public ResponseEntity<Void> reissue(@ExtractValidRefreshToken String refreshToken) {
        String reissuedAccessToken = issueTokenUseCase.reissue(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaderUtils.AUTHORIZATION, HttpHeaderUtils.withBearerToken(reissuedAccessToken))
                .build();
    }
}
