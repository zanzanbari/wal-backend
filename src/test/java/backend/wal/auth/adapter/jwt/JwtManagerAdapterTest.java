package backend.wal.auth.support.token;

import backend.wal.auth.application.port.out.JwtManagerPort;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import backend.wal.auth.adapter.jwt.JwtManagerAdapter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class JwtManagerAdapterTest {

    private static final String SECRET = "JwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKeyJwtSecretKey";
    private static final long ACCESS_TOKEN_EXPIRES_IN = 1000 * 60 * 3;
    private static final long REFRESH_TOKEN_EXPIRES_IN = 1000 * 60 * 5;
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    private static final Long USER_ID = 1L;

    private final JwtManagerPort jwtManager = new JwtManagerAdapter(SECRET, ACCESS_TOKEN_EXPIRES_IN, REFRESH_TOKEN_EXPIRES_IN);

    @DisplayName("유저의 id 를 받아 accessToken 을 생성하면 body 값에 userId 가 설정된다")
    @Test
    void createAccessToken() {
        // when
        String accessToken = jwtManager.createAccessToken(USER_ID);

        // then
        Claims body = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        assertThat(body.get("USER_ID", Long.class)).isEqualTo(USER_ID);
    }

    @DisplayName("userId 값으로 발행된 accessToken 값으로 부터 유저의 아이디 값을 가져온다")
    @Test
    void getLoginUserIdFromAccessToken() {
        // given
        String accessToken = jwtManager.createAccessToken(USER_ID);

        // when
        Long actual = jwtManager.getLoginUserIdFromAccessToken(accessToken);

        // then
        assertThat(actual).isEqualTo(USER_ID);
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