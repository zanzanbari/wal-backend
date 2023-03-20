package backend.wal.auth.support.token;

import backend.wal.auth.domain.entity.RefreshToken;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import backend.wal.support.YamlPropertySourceFactory;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@PropertySource(
        value = "classpath:wal-backend-config/application-jwt.yml",
        factory = YamlPropertySourceFactory.class,
        ignoreResourceNotFound = true)
public class JwtManager {

    private static final String JWT_CLAIM_NAME = "USER_ID";

    private final Key secretKey;
    private final long accessTokenExpiresIn;
    private final long refreshTokenExpiresIn;

    public JwtManager(
            @Value("${jwt.token.secret}") final String secretKey,
            @Value("${jwt.token.expired-time.access}") final long accessTokenExpiresIn,
            @Value("${jwt.token.expired-time.refresh}") final long refreshTokenExpiresIn
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String createAccessToken(Long userId) {
        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + this.accessTokenExpiresIn);

        return Jwts.builder()
                .claim(JWT_CLAIM_NAME, userId)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiresIn)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public RefreshToken createRefreshToken(Long userId) {
        Date now = new Date();
        Date refreshTokenExpiresIn = new Date(now.getTime() + this.refreshTokenExpiresIn);

        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        return RefreshToken.newInstance(userId, refreshToken, refreshTokenExpiresIn);
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            log.error("Invalid JWT Token", e);
            throw UnAuthorizedTokenException.wrong(token);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token", e);
            throw UnAuthorizedTokenException.expired(token);
        }
    }

    public Long getLoginUserIdFromAccessToken(String accessToken) {
        Claims payload = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        return payload.get(JWT_CLAIM_NAME, Long.class);
    }
}
