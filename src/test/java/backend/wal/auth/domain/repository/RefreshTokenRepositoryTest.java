package backend.wal.auth.domain.repository;

import backend.wal.auth.domain.RefreshToken;
import backend.wal.config.JpaRepositoryTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JpaRepositoryTestConfig
class RefreshTokenRepositoryTest {

    private static final String REFRESH_TOKEN_VALUE = "RefreshToken";

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshToken saved;

    @BeforeEach
    void setUp() {
        RefreshToken refreshToken = RefreshToken.newInstance(1L, REFRESH_TOKEN_VALUE, new Date());
        saved = refreshTokenRepository.save(refreshToken);
    }

    @DisplayName("refresh token 값을 받아, 값이 존재하면 refresh 토큰을 가져오고, 없다면 Optional 을 반환한다")
    @ParameterizedTest
    @ValueSource(strings = {"RefreshToken", "null"})
    void findRefreshTokenByValue(String target) {
        // when
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findRefreshTokenByValue(target);

        // then
        if (target.equals("RefreshToken")) {
            assertThat(refreshToken.isPresent()).isTrue();
            assertThat(refreshToken.get()).isEqualTo(saved);
        };
        if (target.equals("null")) {
            assertThat(refreshToken.isPresent()).isFalse();
        };
    }
}