package backend.wal.auth.adapter.jwt;

import backend.wal.auth.application.port.out.JwtManagerPort;
import backend.wal.auth.application.port.out.CreateRefreshTokenResponseDto;
import backend.wal.auth.application.port.out.JwtPayloadInfo;
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
import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource(
        value = "classpath:wal-backend-config/application-jwt.yml",
        factory = YamlPropertySourceFactory.class,
        ignoreResourceNotFound = true)
public final class JwtManagerAdapter implements JwtManagerPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtManagerAdapter.class);

    private static final String JWT_CLAIM_USER_ID = "USER_ID";
    private static final String JWT_CLAIM_ROLE = "ROLE";

    private final Key secretKey;
    private final long accessTokenExpiredIn;
    private final long refreshTokenExpiredIn;

    public JwtManagerAdapter(
            @Value("${jwt.token.secret}") final String secretKey,
            @Value("${jwt.token.expired-time.access}") final long accessTokenExpiredIn,
            @Value("${jwt.token.expired-time.refresh}") final long refreshTokenExpiredIn
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiredIn = accessTokenExpiredIn;
        this.refreshTokenExpiredIn = refreshTokenExpiredIn;
    }

    @Override
    public String createAccessToken(Long userId, String role) {
        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_CLAIM_USER_ID, userId);
        claims.put(JWT_CLAIM_ROLE, role);
        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.accessTokenExpiredIn))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public CreateRefreshTokenResponseDto createRefreshToken(Long userId) {
        Date now = new Date();
        Date expiredIn = new Date(now.getTime() + this.refreshTokenExpiredIn);

        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiredIn)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        return new CreateRefreshTokenResponseDto(userId, refreshToken, expiredIn);
    }

    @Override
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            LOGGER.error("Invalid JWT Token : {}", e.getMessage());
            throw UnAuthorizedTokenException.wrong(token);
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT Token : {}", e.getMessage());
            throw UnAuthorizedTokenException.expired(token);
        }
    }

    @Override
    public JwtPayloadInfo getLoginUserIdFromAccessToken(String accessToken) {
        Claims payload = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        return new JwtPayloadInfo(
                payload.get(JWT_CLAIM_USER_ID, Long.class),
                payload.get(JWT_CLAIM_ROLE, String.class)
        );
    }

    public long getAccessTokenExpiredIn() {
        return accessTokenExpiredIn;
    }
}
