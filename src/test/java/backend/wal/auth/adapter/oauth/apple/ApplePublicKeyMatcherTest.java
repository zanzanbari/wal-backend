package backend.wal.auth.adapter.oauth.apple;

import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKey;
import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKeyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApplePublicKeyMatcherTest {

    private static final String MATCH_ALG = "matchAlg";
    private static final String MATCH_KID = "matchKid";

    private final ApplePublicKeyMatcher applePublicKeyMatcher = new ApplePublicKeyMatcher();

    private final ApplePublicKey applePublicKey1 = new ApplePublicKey(MATCH_ALG, "e", MATCH_KID, "kty", "n", "use");
    private final ApplePublicKey applePublicKey2 = new ApplePublicKey(MATCH_ALG, "e", "kid", "kty", "n", "use");
    private final ApplePublicKey applePublicKey3 = new ApplePublicKey("alg", "e", MATCH_KID, "kty", "n", "use");
    private final ApplePublicKeyResponse applePublicKeyResponse
            = new ApplePublicKeyResponse(List.of(applePublicKey1, applePublicKey2, applePublicKey3));

    @DisplayName("applePublicKeyResponse 에서 tokenHeader 의 alg, kid 가 일치하는 applePublicKey 를 가져온다")
    @Test
    void getMatchKey() {
        // given
        Map<String, String> tokenHeader = Map.of(
                "alg", MATCH_ALG,
                "kid", MATCH_KID
        );

        // when
        ApplePublicKey matchKey = applePublicKeyMatcher.getMatchKey(applePublicKeyResponse, tokenHeader);

        // then
        assertThat(matchKey).isEqualTo(applePublicKey1);
    }
}