package backend.wal.auth.application.service;

import backend.wal.auth.application.port.out.CreateRefreshTokenResponseDto;
import backend.wal.auth.application.port.out.TokenResponseDto;
import backend.wal.auth.application.port.in.IssueTokenUseCase;
import backend.wal.auth.application.port.out.JwtManagerPort;
import backend.wal.auth.domain.repository.RefreshTokenRepository;
import backend.wal.auth.domain.RefreshToken;
import backend.wal.auth.exception.NotFoundRefreshTokenException;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class IssueTokenService implements IssueTokenUseCase {

    private final JwtManagerPort jwtManager;
    private final RefreshTokenRepository refreshTokenRepository;

    public IssueTokenService(final JwtManagerPort jwtManager, final RefreshTokenRepository refreshTokenRepository) {
        this.jwtManager = jwtManager;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public TokenResponseDto issue(Long userId) {
        String accessToken = jwtManager.createAccessToken(userId);
        CreateRefreshTokenResponseDto createRefreshTokenResponseDto = jwtManager.createRefreshToken(userId);
        RefreshToken refreshToken = RefreshToken.newInstance(
                createRefreshTokenResponseDto.getUserId(),
                createRefreshTokenResponseDto.getTokenValue(),
                createRefreshTokenResponseDto.getRefreshTokenExpiresIn()
        );
        refreshTokenRepository.save(refreshToken);
        return new TokenResponseDto(accessToken, refreshToken.getValue());
    }

    @Override
    @Transactional(readOnly = true)
    public String reissue(String validRefreshToken) {
        RefreshToken refreshToken = findByRefreshtoken(validRefreshToken);
        return jwtManager.createAccessToken(refreshToken.getUserId());
    }

    private RefreshToken findByRefreshtoken(String validRefreshToken) {
        return refreshTokenRepository.findRefreshTokenByValue(validRefreshToken)
                .orElseThrow(NotFoundRefreshTokenException::notExists);
    }
}
