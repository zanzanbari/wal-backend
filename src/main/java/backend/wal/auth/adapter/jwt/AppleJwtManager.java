package backend.wal.auth.adapter.jwt;

import backend.wal.auth.exception.UnAuthorizedTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;

@Component
public final class AppleJwtManager {

    private static final String ID_TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AppleJwtClaimValidator appleJwtClaimValidator;

    public AppleJwtManager(final AppleJwtClaimValidator appleJwtClaimValidator) {
        this.appleJwtClaimValidator = appleJwtClaimValidator;
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
        validateClaims(claims);
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

    private void validateClaims(Claims claims) { // FIXME : Exception 재설정
        if (!appleJwtClaimValidator.hasRightIssAndClientId(claims)) {
            throw UnAuthorizedTokenException.unMatched();
        }
        if (!appleJwtClaimValidator.isValidExp(claims)) {
            throw UnAuthorizedTokenException.expired(claims.getId());
        }
    }
}
