package backend.wal.wal.common.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum WalCategoryType {

    COMEDY("드립"),
    FUSS("주접"),
    COMFORT("위로"),
    YELL("꾸중"),
    RESERVATION("예약")
    ;

    private final String value;
}
