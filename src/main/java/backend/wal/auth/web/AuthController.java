package backend.wal.auth.web;

import backend.wal.auth.application.port.in.LoginResponseDto;
import backend.wal.auth.application.port.in.TokenResponseDto;
import backend.wal.auth.application.port.in.IssueTokenUseCase;
import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.provider.AuthServiceProvider;
import backend.wal.auth.web.dto.LoginRequest;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.ExtractValidRefreshToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static backend.wal.support.utils.HttpHeaderUtils.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v2/auth")
public class AuthController {

    private final AuthServiceProvider authServiceProvider;
    private final IssueTokenUseCase issueTokenUseCase;

    public AuthController(final AuthServiceProvider authServiceProvider, final IssueTokenUseCase issueTokenUseCase) {
        this.authServiceProvider = authServiceProvider;
        this.issueTokenUseCase = issueTokenUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request) {
        AuthUseCase authUseCase = authServiceProvider.getAuthServiceBy(request.getSocialType());

        LoginResponseDto loginResponseDto = authUseCase.login(request.toAuthServiceDto());
        TokenResponseDto tokenResponseDto = issueTokenUseCase.issue(loginResponseDto.getUserId());

        if (loginResponseDto.isNewUser()) {
            return ResponseEntity.status(CREATED)
                    .header(AUTHORIZATION, withBearerToken(tokenResponseDto.getAccessToken()))
                    .header(REFRESH_TOKEN, tokenResponseDto.getRefreshToken())
                    .build();
        }

        return ResponseEntity.ok()
                .header(AUTHORIZATION, withBearerToken(tokenResponseDto.getAccessToken()))
                .header(REFRESH_TOKEN, tokenResponseDto.getRefreshToken())
                .build();
    }

    @Authentication
    @GetMapping("/reissue")
    public ResponseEntity<Void> reissue(@ExtractValidRefreshToken String refreshToken) {
        String reissuedAccessToken = issueTokenUseCase.reissue(refreshToken);
        return ResponseEntity.ok()
                .header(AUTHORIZATION, withBearerToken(reissuedAccessToken))
                .build();
    }
}
