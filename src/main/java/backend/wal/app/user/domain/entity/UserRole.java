package backend.wal.app.user.domain.entity;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum UserRole {

    ADMIN("관리자"),
    USER("일반 유저"),
    ;

    private final String value;
}
