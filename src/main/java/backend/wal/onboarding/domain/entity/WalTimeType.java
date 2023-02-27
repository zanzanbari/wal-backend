package backend.wal.app.onboarding.domain.entity;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum WalTimeType {

    MORNING("아침"),
    AFTERNOON("점심"),
    NIGHT("저녁"),
    RESERVATION("예약"),
    ;

    private final String value;
}
