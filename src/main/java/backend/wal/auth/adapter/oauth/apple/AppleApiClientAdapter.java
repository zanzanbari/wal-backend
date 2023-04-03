package backend.wal.auth.adapter.oauth.apple;

import backend.wal.auth.adapter.jwt.AppleJwtManager;
import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKey;
import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKeyResponse;
import backend.wal.auth.application.port.out.OAuthApiClientPort;
import backend.wal.auth.application.port.out.OAuthUserInfoResponseDto;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@Component
public class AppleApiClientAdapter implements OAuthApiClientPort {

    private final AppleAuthApiClient appleAuthApiClient;
    private final AppleJwtManager appleJwtManager;
    private final ApplePublicKeyMatcher applePublicKeyMatcher;
    private final PublicKeyGenerator publicKeyGenerator;

    public AppleApiClientAdapter(final AppleAuthApiClient appleAuthApiClient,
                                 final AppleJwtManager appleJwtManager,
                                 final ApplePublicKeyMatcher applePublicKeyMatcher,
                                 final PublicKeyGenerator publicKeyGenerator) {
        this.appleAuthApiClient = appleAuthApiClient;
        this.appleJwtManager = appleJwtManager;
        this.applePublicKeyMatcher = applePublicKeyMatcher;
        this.publicKeyGenerator = publicKeyGenerator;
    }

    @Override
    public OAuthUserInfoResponseDto getOAuthUserId(String token) {
        ApplePublicKeyResponse publicKeyResponse = appleAuthApiClient.getPublicKeys();
        Map<String, String> tokenHeader = appleJwtManager.parseHeaders(token);
        ApplePublicKey matchKey = applePublicKeyMatcher.getMatchKey(publicKeyResponse, tokenHeader);
        PublicKey publicKey = publicKeyGenerator.generate(matchKey);
        return new OAuthUserInfoResponseDto(appleJwtManager.getSubject(publicKey, token), "DEFAULT");
    }
}
