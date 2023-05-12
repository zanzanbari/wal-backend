package backend.wal.auth.adapter.jwt;

import backend.wal.auth.adapter.oauth.apple.AppleClaimsValidator;
import backend.wal.auth.exception.UnAuthorizedTokenException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;

import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class AppleJwtManager {

    private static final String ID_TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AppleClaimsValidator appleClaimsValidator;

    public AppleJwtManager(final AppleClaimsValidator appleClaimsValidator) {
        this.appleClaimsValidator = appleClaimsValidator;
    }

    public Map<String, String> parseHeaders(String idToken) {
        try {
            String encodedHeader = idToken.split(ID_TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
            String decodedHeader = new String(Base64.getDecoder().decode(encodedHeader));
            return OBJECT_MAPPER.readValue(decodedHeader, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw UnAuthorizedTokenException.wrong(idToken);
        }
    }

    public String getSubject(PublicKey publicKey, String idToken) {
        Claims claims = parseClaims(publicKey, idToken);
        validateIssuerAndAudience(claims.getIssuer(), claims.getAudience());
        validateExpiresIn(claims.getExpiration(), idToken);
        return claims.getSubject();
    }

    private Claims parseClaims(PublicKey publicKey, String idToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(idToken)
                    .getBody();
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            throw UnAuthorizedTokenException.wrong(idToken);
        } catch (ExpiredJwtException e) {
            throw UnAuthorizedTokenException.expired(idToken);
        }
    }

    private void validateIssuerAndAudience(String issuer, String audience) {
        if (!appleClaimsValidator.hasRightIssAndClientId(issuer, audience)) {
            throw UnAuthorizedTokenException.unMatchedClaims();
        }
    }

    private void validateExpiresIn(Date expiration, String token) {
        if (!appleClaimsValidator.isValidExp(expiration.getTime(), new Date().getTime())) {
            throw UnAuthorizedTokenException.expired(token);
        }
    }
}
