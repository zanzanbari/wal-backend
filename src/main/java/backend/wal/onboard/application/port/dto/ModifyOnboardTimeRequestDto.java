package backend.wal.onboard.application.port.dto;

import backend.wal.onboard.domain.common.WalTimeType;
import lombok.Getter;

import java.util.Set;

@Getter
public final class ModifyOnboardTimeRequestDto {

    private final Set<WalTimeType> timeTypes;

    public ModifyOnboardTimeRequestDto(final Set<WalTimeType> timeTypes) {
        this.timeTypes = timeTypes;
    }
}
