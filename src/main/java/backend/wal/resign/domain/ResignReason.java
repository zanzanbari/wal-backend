package backend.wal.resign.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResignReason {

    DO_NOT_LIKE("왈소리가 마음에 들지 않아요"),
    WANT_NEW_ACCOUNT("새로운 계정을 만들고 싶어요"),
    PROTECT_PERSONAL_INFO("개인 정보를 보호하고 싶어요"),
    INFREQUENTLY_USED("사용 빈도수가 적어요"),
    ;

    private final String value;
}
