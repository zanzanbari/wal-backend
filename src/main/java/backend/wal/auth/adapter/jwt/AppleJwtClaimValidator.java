package backend.wal.auth.adapter.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public final class AppleJwtClaimValidator {

    private final String iss;
    private final String clientId;

    public AppleJwtClaimValidator(@Value("${oauth.apple.iss}") String iss,
                                  @Value("${oauth.apple.client-id}") String clientId) {
        this.iss = iss;
        this.clientId = clientId;
    }

    public boolean hasRightIssAndClientId(Claims claims) {
        return claims.getIssuer().equals(iss)
                && claims.getAudience().equals(clientId);
    }

    public boolean isValidExp(Claims claims) {
        Date expiration = claims.getExpiration();
        Date now = new Date();
        return expiration.after(now);
    }
}
