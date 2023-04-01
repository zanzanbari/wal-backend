package backend.wal.user.web.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NicknameInfoResponse {

    String nickname;

    public NicknameInfoResponse(final String nickname) {
        this.nickname = nickname;
    }
}
