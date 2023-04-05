package backend.wal.auth.adapter.oauth.apple;

import backend.wal.auth.adapter.jwt.AppleJwtManager;
import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKey;
import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKeyResponse;
import backend.wal.auth.application.port.out.OAuthUserInfoResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AppleApiClientAdapterTest {

    private static final String DUMMY_ID_TOKEN = "APPLE_ID_TOKEN";
    private static final String DEFAULT_NICKNAME = "DEFAULT";

    private final AppleAuthApiClient appleAuthApiClient = mock(AppleAuthApiClient.class);
    private final AppleJwtManager appleJwtManager = mock(AppleJwtManager.class);
    private final ApplePublicKeyMatcher applePublicKeyMatcher = mock(ApplePublicKeyMatcher.class);
    private final PublicKeyGenerator publicKeyGenerator = mock(PublicKeyGenerator.class);

    private final AppleApiClientAdapter appleApiClientAdapter =
            new AppleApiClientAdapter(appleAuthApiClient, appleJwtManager, applePublicKeyMatcher, publicKeyGenerator);

    @DisplayName("idToken 으로 OAuth 유저의 아이디를 가져온다")
    @Test
    @SuppressWarnings("unchecked")
    void getOAuthUserId() {
        // given
        String socialId = "socialId";
        when(appleAuthApiClient.getPublicKeys())
                .thenReturn(mock(ApplePublicKeyResponse.class));
        when(appleJwtManager.parseHeaders(DUMMY_ID_TOKEN))
                .thenReturn(mock(Map.class));
        when(applePublicKeyMatcher.getMatchKey(any(ApplePublicKeyResponse.class), anyMap()))
                .thenReturn(mock(ApplePublicKey.class));
        when(publicKeyGenerator.generate(any(ApplePublicKey.class)))
                .thenReturn(mock(PublicKey.class));
        when(appleJwtManager.getSubject(any(PublicKey.class), anyString()))
                .thenReturn(socialId);

        // when
        OAuthUserInfoResponseDto oAuthUserInfo = appleApiClientAdapter.getOAuthUserId(DUMMY_ID_TOKEN);

        // then
        assertThat(oAuthUserInfo.getId()).isEqualTo(socialId);
        assertThat(oAuthUserInfo.getNickname()).isEqualTo(DEFAULT_NICKNAME);
    }
}