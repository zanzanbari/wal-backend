package backend.wal.auth.app.service;

import backend.wal.advice.exception.NotFoundException;
import backend.wal.auth.app.dto.response.TokenResponse;
import backend.wal.auth.domain.entity.RefreshToken;
import backend.wal.auth.domain.repository.RefreshTokenRepository;
import backend.wal.auth.support.token.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtManager jwtManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenResponse issueToken(Long userId) {
        String accessToken = jwtManager.createAccessToken(userId);
        RefreshToken refreshToken = jwtManager.createRefreshToken(userId);
        refreshTokenRepository.save(refreshToken);
        return new TokenResponse(accessToken, refreshToken.getValue());
    }

    public String reissueToken(String validRefreshToken) {
        RefreshToken refreshToken = findByRefreshtoken(validRefreshToken);
        return jwtManager.createAccessToken(refreshToken.getUserId());
    }

    private RefreshToken findByRefreshtoken(String validRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenByValue(validRefreshToken);
        if (refreshToken == null) {
            throw new NotFoundException("존재하지 않는 refreshToken 입니다");
        }
        return refreshToken;
    }
}
