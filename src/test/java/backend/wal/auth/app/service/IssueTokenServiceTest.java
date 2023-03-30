package backend.wal.auth.app.service;

import backend.wal.advice.exception.NotFoundException;
import backend.wal.auth.application.port.out.TokenResponseDto;
import backend.wal.auth.application.service.IssueTokenService;
import backend.wal.auth.application.port.out.JwtManagerPort;
import backend.wal.auth.domain.RefreshToken;
import backend.wal.auth.domain.repository.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssueTokenServiceTest {

    private static final Long USER_ID = 1L;
    private static final String ACCESS_TOKEN = "AccessToken";
    private static final String REFRESH_TOKEN = "RefreshToken";

    @Mock
    private JwtManagerPort jwtManager;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private IssueTokenService issueTokenService;

    private RefreshToken refreshToken;

    @DisplayName("유저의 아이디를 받아 accessToken, refreshToken 을 발급힌다")
    @Test
    void issueToken() {
        // given
        refreshToken = RefreshToken.newInstance(USER_ID, REFRESH_TOKEN, new Date());
        when(jwtManager.createAccessToken(USER_ID))
                .thenReturn(ACCESS_TOKEN);
        when(jwtManager.createRefreshToken(USER_ID))
                .thenReturn(refreshToken);

        // when
        TokenResponseDto response = issueTokenService.issue(USER_ID);

        // then
        assertAll(
                () -> assertThat(response.getAccessToken()).isEqualTo(ACCESS_TOKEN),
                () -> assertThat(response.getRefreshToken()).isEqualTo(refreshToken.getValue())
        );
    }

    @DisplayName("유효한 refreshToken 을 받아 새로운 accessToken 을 발급한다")
    @Test
    void reissueToken() {
        // given
        refreshToken = RefreshToken.newInstance(USER_ID, REFRESH_TOKEN, new Date());
        when(refreshTokenRepository.findRefreshTokenByValue(REFRESH_TOKEN))
                .thenReturn(Optional.of(refreshToken));
        when(jwtManager.createAccessToken(USER_ID))
                .thenReturn("NEW_ACCESS_TOKEN");

        // when
        String newAccessToken = issueTokenService.reissue(REFRESH_TOKEN);

        // then
        assertThat(newAccessToken).isEqualTo("NEW_ACCESS_TOKEN");
    }

    @DisplayName("유효한 refreshToken 에 해당하는 값이 없으면 에러가 발생한다")
    @Test
    void test() {
        // given
        when(refreshTokenRepository.findRefreshTokenByValue(REFRESH_TOKEN))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> issueTokenService.reissue(REFRESH_TOKEN))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 refreshToken 입니다");
    }
}