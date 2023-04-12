package backend.wal.user.domain.aggregate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserStatus {

    ACTIVE("활성"),
    DELETED("탈퇴"),
    ;

    private final String value;
}
