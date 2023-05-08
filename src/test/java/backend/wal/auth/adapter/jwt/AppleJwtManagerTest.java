package backend.wal.auth.adapter.jwt;

import backend.wal.auth.adapter.oauth.apple.AppleClaimsValidator;
import backend.wal.auth.exception.UnAuthorizedTokenException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AppleJwtManagerTest {

    private static final String ALG = "alg";
    private static final String KID = "kid";
    private static final String ALGORITHM = "RSA";
    private static final String ISSUER = "iss";
    private static final String AUDIENCE = "clientId";
    private static final String SUBJECT = "subject";
    private static final long VALID_EXPIRED_TIME = new Date().getTime() + 60_000;

    private final AppleClaimsValidator appleClaimsValidator = new AppleClaimsValidator(ISSUER, AUDIENCE);
    private final AppleJwtManager appleJwtManager = new AppleJwtManager(appleClaimsValidator);

    @DisplayName("유효하지 않은 idToken 값이 들어오면 에러가 발생한다")
    @Test
    void fail_parseHeaders() {
        // given
        String invalidIdToken = "invalidIdToken";

        // when, then
        assertThatThrownBy(() -> appleJwtManager.parseHeaders(invalidIdToken))
                .isInstanceOf(UnAuthorizedTokenException.class)
                .hasMessage(String.format("잘못된 토큰 (%s) 입니다", invalidIdToken));
    }

    @DisplayName("유효한 idToken 을 받아 header 를 파싱해 alg, kid 키를 포함한 디코딩된 헤더 정보를 가져온다")
    @Test
    void parseHeaders() throws NoSuchAlgorithmException {
        // given
        KeyPair keyPair = getGenerateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        String idToken = generateTestTokenByPrivateKey(privateKey);

        // when
        Map<String, String> decodedHeader = appleJwtManager.parseHeaders(idToken);

        // then
        assertThat(decodedHeader).containsKeys(ALG, KID);
    }

    @DisplayName("동일한 암호화 알고리즘으로 생성된 공개키와 idToken 값을 받아 subject 정보를 가져온다")
    @Test
    void getSubject() throws NoSuchAlgorithmException {
        // given
        KeyPair keyPair = getGenerateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String idToken = generateTestTokenByPrivateKey(privateKey);

        // when
        String subject = appleJwtManager.getSubject(publicKey, idToken);

        // then
        assertThat(subject).isEqualTo(SUBJECT);
    }

    private KeyPair getGenerateKeyPair() throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(ALGORITHM)
                .generateKeyPair();
    }

    private String generateTestTokenByPrivateKey(PrivateKey privateKey) {
        return Jwts.builder()
                .setHeaderParam(ALG, ALGORITHM)
                .setHeaderParam(KID, "kid")
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setSubject(SUBJECT)
                .setExpiration(new Date(VALID_EXPIRED_TIME))
                .signWith(privateKey)
                .compact();
    }
}