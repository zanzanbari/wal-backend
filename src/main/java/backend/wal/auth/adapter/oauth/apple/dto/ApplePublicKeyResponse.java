package backend.wal.auth.adapter.oauth.apple.dto;

import java.util.Collections;
import java.util.List;

public class ApplePublicKeyResponse {

    private List<ApplePublicKey> keys;

    private ApplePublicKeyResponse() {
    }

    public ApplePublicKeyResponse(final List<ApplePublicKey> keys) {
        this.keys = keys;
    }

    public List<ApplePublicKey> getKeys() {
        return Collections.unmodifiableList(keys);
    }
}
