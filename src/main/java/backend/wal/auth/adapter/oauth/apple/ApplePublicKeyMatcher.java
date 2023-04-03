package backend.wal.auth.adapter.oauth.apple;

import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKey;
import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKeyResponse;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplePublicKeyMatcher {

    private static final String ALG_KEY = "alg";
    private static final String KID_KEY = "kid";

    public ApplePublicKey getMatchKey(ApplePublicKeyResponse applePublicKeyResponse, Map<String, String> tokenHeader) {
        return applePublicKeyResponse.getKeys()
                .stream()
                .filter(publicKey -> publicKey.getAlg().equals(tokenHeader.get(ALG_KEY)))
                .filter(publicKey -> publicKey.getKid().equals(tokenHeader.get(KID_KEY)))
                .findFirst()
                .orElseThrow(UnAuthorizedTokenException::unMatched);
    }
}
