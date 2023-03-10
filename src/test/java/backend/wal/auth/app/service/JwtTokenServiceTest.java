package backend.wal.auth.app.service;

import backend.wal.advice.exception.NotFoundException;
import backend.wal.auth.app.dto.response.TokenResponse;
import backend.wal.auth.domain.entity.RefreshToken;
import backend.wal.auth.domain.repository.RefreshTokenRepository;
import backend.wal.auth.support.token.JwtManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceTest {

    private static final Long USER_ID = 1L;
    private static final String ACCESS_TOKEN = "AccessToken";
    private static final String REFRESH_TOKEN = "RefreshToken";

    @Mock
    private JwtManager jwtManager;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private JwtTokenService jwtTokenService;

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
        TokenResponse response = jwtTokenService.issueToken(USER_ID);

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
                .thenReturn(refreshToken);
        when(jwtManager.createAccessToken(USER_ID))
                .thenReturn("NEW_ACCESS_TOKEN");

        // when
        String newAccessToken = jwtTokenService.reissueToken(REFRESH_TOKEN);

        // then
        assertThat(newAccessToken).isEqualTo("NEW_ACCESS_TOKEN");
    }

    @DisplayName("유효한 refreshToken 에 해당하는 값이 없으면 에러가 발생한다")
    @Test
    void test() {
        // given
        when(refreshTokenRepository.findRefreshTokenByValue(REFRESH_TOKEN))
                .thenReturn(null);

        // when, then
        assertThatThrownBy(() -> jwtTokenService.reissueToken(REFRESH_TOKEN))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 refreshToken 입니다");
    }
}