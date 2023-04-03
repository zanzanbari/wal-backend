package backend.wal.auth.adapter.oauth.apple.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplePublicKeyResponse {

    private List<ApplePublicKey> keys;

    public ApplePublicKeyResponse(final List<ApplePublicKey> keys) {
        this.keys = keys;
    }

    public List<ApplePublicKey> getKeys() {
        return Collections.unmodifiableList(keys);
    }
}
