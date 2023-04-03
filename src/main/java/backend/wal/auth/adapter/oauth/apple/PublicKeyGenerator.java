package backend.wal.auth.adapter.oauth.apple;

import backend.wal.auth.adapter.oauth.apple.dto.ApplePublicKey;
import backend.wal.auth.exception.InternalAuthServerException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Component
public class PublicKeyGenerator {

    private static final int POSITIVE_SIGNUM = 1;

    public PublicKey generate(ApplePublicKey applePublicKey) {
        byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.getN());
        byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.getE());

        BigInteger n = new BigInteger(POSITIVE_SIGNUM, nBytes);
        BigInteger e = new BigInteger(POSITIVE_SIGNUM, eBytes);

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.getKty());
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw InternalAuthServerException.cannotGeneratePublicKey();
        }
    }
}
