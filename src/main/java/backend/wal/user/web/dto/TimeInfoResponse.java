package backend.wal.user.web.dto;

import backend.wal.wal.common.domain.WalTimeType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeInfoResponse {

    private Set<WalTimeType> timeInfo;

    public TimeInfoResponse(final Set<WalTimeType> timeInfo) {
        this.timeInfo = timeInfo;
    }

    public Set<WalTimeType> getTimeInfo() {
        return timeInfo;
    }
}
