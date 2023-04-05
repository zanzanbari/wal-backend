package backend.wal.auth.adapter.jwt;

import backend.wal.auth.application.port.out.JwtManagerPort;
import backend.wal.auth.application.port.out.CreateRefreshTokenResponseDto;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import backend.wal.support.YamlPropertySourceFactory;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@PropertySource(
        value = "classpath:wal-backend-config/application-jwt.yml",
        factory = YamlPropertySourceFactory.class,
        ignoreResourceNotFound = true)
public final class JwtManagerAdapter implements JwtManagerPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtManagerAdapter.class);

    private static final String JWT_CLAIM_NAME = "USER_ID";

    private final Key secretKey;
    private final long accessTokenExpiresIn;
    private final long refreshTokenExpiresIn;

    public JwtManagerAdapter(
            @Value("${jwt.token.secret}") final String secretKey,
            @Value("${jwt.token.expired-time.access}") final long accessTokenExpiresIn,
            @Value("${jwt.token.expired-time.refresh}") final long refreshTokenExpiresIn
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    @Override
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

    @Override
    public CreateRefreshTokenResponseDto createRefreshToken(Long userId) {
        Date now = new Date();
        Date refreshTokenExpiresIn = new Date(now.getTime() + this.refreshTokenExpiresIn);

        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        return new CreateRefreshTokenResponseDto(userId, refreshToken, refreshTokenExpiresIn);
    }

    @Override
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            LOGGER.error("Invalid JWT Token", e);
            throw UnAuthorizedTokenException.wrong(token);
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT Token", e);
            throw UnAuthorizedTokenException.expired(token);
        }
    }

    @Override
    public Long getLoginUserIdFromAccessToken(String accessToken) {
        Claims payload = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        return payload.get(JWT_CLAIM_NAME, Long.class);
    }
}
