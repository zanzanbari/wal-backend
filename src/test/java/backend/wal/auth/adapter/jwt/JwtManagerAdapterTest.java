package backend.wal.auth.adapter.jwt;

import backend.wal.auth.application.port.out.CreateRefreshTokenResponseDto;
import backend.wal.auth.application.port.out.JwtManagerPort;
import backend.wal.auth.application.port.out.JwtPayloadInfo;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class JwtManagerAdapterTest {

    private static final String SECRET = "JwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKey";
    private static final long ACCESS_TOKEN_EXPIRES_IN = 1000 * 60 * 3;
    private static final long REFRESH_TOKEN_EXPIRES_IN = 1000 * 60 * 5;
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    private static final Long USER_ID = 1L;
    private static final String ROLE = "USER";

    private final JwtManagerPort jwtManager = new JwtManagerAdapter(SECRET, ACCESS_TOKEN_EXPIRES_IN, REFRESH_TOKEN_EXPIRES_IN);

    @DisplayName("유저의 id 를 받아 accessToken 을 생성하면 body 값에 userId 와 role 이 설정된다")
    @Test
    void createAccessToken() {
        // when
        String accessToken = jwtManager.createAccessToken(USER_ID, ROLE);

        // then
        Claims body = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        assertAll(
                () -> assertThat(body.get("USER_ID", Long.class)).isEqualTo(USER_ID),
                () -> assertThat(body.get("ROLE", String.class)).isEqualTo(ROLE)
        );
    }

    @DisplayName("유저의 id 를 받아 refreshToken 을 생성하면 정해진 리프레시 토큰 만료시간을 갖는 refreshToken 이 설정된다")
    @Test
    void createRefreshToken() {
        // given
        Date refreshExpired = new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRES_IN) ;

        // when
        CreateRefreshTokenResponseDto createTokenDto = jwtManager.createRefreshToken(USER_ID);

        // then
        Claims body = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(createTokenDto.getTokenValue())
                .getBody();
        assertThat(body.getExpiration()).isBeforeOrEqualTo(refreshExpired);
    }

    @DisplayName("userId 값으로 발행된 accessToken 값으로 부터 userId 값과 role 값을 가져온다")
    @Test
    void getLoginUserIdFromAccessToken() {
        // given
        String accessToken = jwtManager.createAccessToken(USER_ID, ROLE);

        // when
        JwtPayloadInfo actual = jwtManager.getLoginUserIdFromAccessToken(accessToken);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(USER_ID),
                () -> assertThat(actual.getRole()).isEqualTo(ROLE)
        );
    }

    @DisplayName("잘못된 토큰을 검증하면 예외가 발생한다")
    @Test
    void fail_validateToken_invalidTokenError() {
        // when, then
        assertThatThrownBy(() -> jwtManager.validateToken("invalidToken"))
                .isInstanceOf(UnAuthorizedTokenException.class)
                .hasMessage(String.format("잘못된 토큰 (%s) 입니다", "invalidToken"));
    }

    @DisplayName("만료된 토큰을 검증하면 예외가 발생한다")
    @Test
    void fail_validateToken_expiredTokenError() {
        // given
        String expiredToken = generateExpiredToken();

        // when, then
        assertThatThrownBy(() -> jwtManager.validateToken(expiredToken))
                .isInstanceOf(UnAuthorizedTokenException.class)
                .hasMessage(String.format("만료된 토큰 (%s) 입니다", expiredToken));
    }

    private String generateExpiredToken() {
        Date now = new Date();
        Date expriedDate = new Date(now.getTime() - 1);
        return Jwts.builder()
                .claim("USER_ID", USER_ID)
                .setIssuedAt(now)
                .setExpiration(expriedDate)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }
}