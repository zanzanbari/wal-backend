package backend.wal.onboarding.app.dto.request;

import backend.wal.onboarding.domain.entity.WalTimeType;
import lombok.Getter;

import java.util.Set;

@Getter
public final class ModifyOnboardTimeRequestDto {

    private final Set<WalTimeType> timeTypes;

    public ModifyOnboardTimeRequestDto(final Set<WalTimeType> timeTypes) {
        this.timeTypes = timeTypes;
    }
}
