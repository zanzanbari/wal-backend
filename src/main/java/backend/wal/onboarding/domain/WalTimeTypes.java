package backend.wal.onboarding.domain;

import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.wal.domain.entity.TodayWal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class WalTimeTypes {

    private static final ZoneId KOREA_TIME_ZONE = ZoneId.of("Asia/Seoul");

    private final Set<WalTimeType> values;

    private WalTimeTypes(final Set<WalTimeType> values) {
        this.values = values;
    }

    public static WalTimeTypes createCompareAfterNow(final Set<WalTimeType> timeTypes) {
        return new WalTimeTypes(extractAfterNow(timeTypes));
    }

    public static WalTimeTypes createCompareAfterNow(final List<TodayWal> todayWals) {
        Set<WalTimeType> walTimeTypes = todayWals.stream()
                .map(TodayWal::getTimeType)
                .collect(Collectors.toSet());
        return new WalTimeTypes(extractAfterNow(walTimeTypes));
    }

    private static Set<WalTimeType> extractAfterNow(Set<WalTimeType> timeTypes) {
        LocalDateTime now = LocalDateTime.now(KOREA_TIME_ZONE);
        return timeTypes.stream()
                .map(timeType -> timeType.findAfterNow(now))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public boolean isExist() {
        return !values.isEmpty();
    }

    public Set<WalTimeType> getValues() {
        return Collections.unmodifiableSet(values);
    }
}
