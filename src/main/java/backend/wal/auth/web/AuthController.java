package backend.wal.auth.web;

import static backend.wal.support.utils.HttpHeaderUtils.*;
import static org.springframework.http.HttpStatus.*;

import backend.wal.auth.application.port.in.LoginResponseDto;
import backend.wal.auth.application.port.in.TokenResponseDto;
import backend.wal.auth.application.port.in.IssueTokenUseCase;
import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.provider.AuthServiceProvider;
import backend.wal.auth.web.dto.LoginRequest;
import backend.wal.auth.web.dto.LoginResponse;
import backend.wal.support.annotation.ExtractValidRefreshToken;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthUseCase authUseCase = authServiceProvider.getAuthServiceBy(request.getSocialType());
        LoginResponseDto loginResponseDto = authUseCase.login(request.toAuthServiceDto());
        LoginResponse loginResponse = new LoginResponse(loginResponseDto.getNickname());

        if (loginResponseDto.isNewUser()) {
            TokenResponseDto tokenResponseDto = issueTokenUseCase.issueForNewUser(
                    loginResponseDto.getUserId(),
                    loginResponseDto.getRole()
            );
            return ResponseEntity.status(CREATED)
                    .header(AUTHORIZATION, withBearerToken(tokenResponseDto.getAccessToken()))
                    .header(REFRESH_TOKEN, tokenResponseDto.getRefreshToken())
                    .body(loginResponse);
        }

        TokenResponseDto tokenResponseDto = issueTokenUseCase.issueForAlreadyUser(
                loginResponseDto.getUserId(),
                loginResponseDto.getRole()
        );
        return ResponseEntity.ok()
                .header(AUTHORIZATION, withBearerToken(tokenResponseDto.getAccessToken()))
                .header(REFRESH_TOKEN, tokenResponseDto.getRefreshToken())
                .body(loginResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<Void> reissue(@ExtractValidRefreshToken String refreshToken) {
        String reissuedAccessToken = issueTokenUseCase.reissue(refreshToken, "USER");
        return ResponseEntity.ok()
                .header(AUTHORIZATION, withBearerToken(reissuedAccessToken))
                .build();
    }
}
