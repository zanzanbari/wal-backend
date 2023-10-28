package backend.wal.auth.application.service;

import backend.wal.advice.exception.NotFoundException;
import backend.wal.auth.application.port.out.CreateRefreshTokenResponseDto;
import backend.wal.auth.application.port.in.TokenResponseDto;
import backend.wal.auth.application.port.out.JwtManagerPort;
import backend.wal.auth.domain.RefreshToken;
import backend.wal.auth.domain.repository.RefreshTokenRepository;
import backend.wal.support.Role;
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
    private static final String USER_ROLE = Role.USER.name();

    @Mock
    private JwtManagerPort jwtManagerPort;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private IssueTokenService issueTokenService;

    @DisplayName("새로운 유저의 아이디를 받아 accessToken, refreshToken 을 발급힌다")
    @Test
    void issueForNewUser() {
        // given
        CreateRefreshTokenResponseDto createTokenDto = new CreateRefreshTokenResponseDto(USER_ID, REFRESH_TOKEN, new Date());
        when(jwtManagerPort.createAccessToken(USER_ID, USER_ROLE))
                .thenReturn(ACCESS_TOKEN);
        when(jwtManagerPort.createRefreshToken(USER_ID))
                .thenReturn(createTokenDto);

        // when
        TokenResponseDto response = issueTokenService.issueForNewUser(USER_ID, USER_ROLE);

        // then
        assertAll(
                () -> assertThat(response.getAccessToken()).isEqualTo(ACCESS_TOKEN),
                () -> assertThat(response.getRefreshToken()).isEqualTo(REFRESH_TOKEN)
        );
    }

    @DisplayName("기존 유저의 아이디를 받아 accessToken, refreshToken 을 발급힌다")
    @Test
    void issueForAlreadyUser() {
        // given
        CreateRefreshTokenResponseDto createTokenDto = new CreateRefreshTokenResponseDto(USER_ID, REFRESH_TOKEN, new Date());
        when(refreshTokenRepository.findRefreshTokenByUserId(USER_ID))
                .thenReturn(Optional.of(RefreshToken.newInstance(USER_ID, REFRESH_TOKEN, new Date())));
        when(jwtManagerPort.createAccessToken(USER_ID, USER_ROLE))
                .thenReturn(ACCESS_TOKEN);
        when(jwtManagerPort.createRefreshToken(USER_ID))
                .thenReturn(createTokenDto);

        // when
        TokenResponseDto response = issueTokenService.issueForAlreadyUser(USER_ID, USER_ROLE);

        // then
        assertAll(
                () -> assertThat(response.getAccessToken()).isEqualTo(ACCESS_TOKEN),
                () -> assertThat(response.getRefreshToken()).isEqualTo(REFRESH_TOKEN)
        );
    }

    @DisplayName("유효한 refreshToken 을 받아 새로운 accessToken 을 발급한다")
    @Test
    void reissueToken() {
        // given
        RefreshToken refreshToken = RefreshToken.newInstance(USER_ID, REFRESH_TOKEN, new Date());
        when(refreshTokenRepository.findRefreshTokenByValue(REFRESH_TOKEN))
                .thenReturn(Optional.of(refreshToken));
        when(jwtManagerPort.createAccessToken(USER_ID, USER_ROLE))
                .thenReturn("NEW_ACCESS_TOKEN");

        // when
        String newAccessToken = issueTokenService.reissue(REFRESH_TOKEN, USER_ROLE);

        // then
        assertThat(newAccessToken).isEqualTo("NEW_ACCESS_TOKEN");
    }

    @DisplayName("유효한 refreshToken 에 해당하는 값이 없으면 에러가 발생한다")
    @Test
    void fail_issueForAlreadyUser() {
        // given
        when(refreshTokenRepository.findRefreshTokenByUserId(USER_ID))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> issueTokenService.issueForAlreadyUser(USER_ID, USER_ROLE))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 refreshToken 입니다");
    }

    @DisplayName("유효한 refreshToken 에 해당하는 값이 없으면 에러가 발생한다")
    @Test
    void fail_reissueToken() {
        // given
        when(refreshTokenRepository.findRefreshTokenByValue(REFRESH_TOKEN))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> issueTokenService.reissue(REFRESH_TOKEN, USER_ROLE))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 refreshToken 입니다");
    }
}