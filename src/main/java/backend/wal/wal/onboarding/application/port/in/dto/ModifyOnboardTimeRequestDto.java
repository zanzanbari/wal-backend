package backend.wal.wal.onboarding.application.port.dto;

import backend.wal.wal.common.domain.aggregate.WalTimeType;
import lombok.Getter;

import java.util.Set;

@Getter
public final class ModifyOnboardTimeRequestDto {

    private final Set<WalTimeType> timeTypes;

    public ModifyOnboardTimeRequestDto(final Set<WalTimeType> timeTypes) {
        this.timeTypes = timeTypes;
    }
}
