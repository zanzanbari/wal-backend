package backend.wal.reservation.domain.aggregate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SendStatus {

    DONE("전송 완료"),
    NOT_DONE("전송 예정"),
    ;

    private final String value;

    public String getValue() {
        return value;
    }
}
