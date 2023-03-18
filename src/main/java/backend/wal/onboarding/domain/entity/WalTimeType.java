package backend.wal.onboarding.domain.entity;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum WalTimeType {

    MORNING("아침", LocalTime.of(8, 0, 0)),
    AFTERNOON("점심", LocalTime.of(14, 0, 0)),
    NIGHT("저녁", LocalTime.of(20, 0, 0)),
    RESERVATION("예약", null),
    ;

    private final String value;
    private final LocalTime sendTime;

    public WalTimeType findAfterNow(LocalDateTime now) {
        return isAfterNow(now) ? this : null;
    }

    public boolean isAfterNow(LocalDateTime now) {
        LocalDateTime sendDateTime = LocalDateTime.of(now.toLocalDate(), this.sendTime);
        return sendDateTime.isAfter(now);
    }
}
