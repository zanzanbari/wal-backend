package backend.wal.user.application.port.out;

import backend.wal.user.web.dto.TimeInfoResponse;
import backend.wal.wal.common.domain.WalTimeType;

import java.util.Set;

public final class TimeTypeResponseDto {

    private final Set<WalTimeType> timeInfo;

    public TimeTypeResponseDto(final Set<WalTimeType> timeInfo) {
        this.timeInfo = timeInfo;
    }

    public TimeInfoResponse toWebResponse() {
        return new TimeInfoResponse(timeInfo);
    }
}
