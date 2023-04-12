package backend.wal.auth.adapter.oauth.apple;

import backend.wal.support.YamlPropertySourceFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(
        value = "classpath:wal-backend-config/application-apple.yml",
        factory = YamlPropertySourceFactory.class,
        ignoreResourceNotFound = true)
public final class AppleClaimsValidator {

    private final String iss;
    private final String clientId;

    public AppleClaimsValidator(@Value("${oauth.apple.iss}") String iss,
                                @Value("${oauth.apple.client-id}") String clientId) {
        this.iss = iss;
        this.clientId = clientId;
    }

    public boolean hasRightIssAndClientId(String issuer, String audience) {
        return issuer.equals(iss)
                && audience.equals(clientId);
    }

    public boolean isValidExp(long expiration, long now) {
        return expiration > now;
    }
}
