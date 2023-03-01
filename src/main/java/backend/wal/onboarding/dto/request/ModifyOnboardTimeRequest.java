package backend.wal.onboarding.dto.request;

import backend.wal.onboarding.domain.entity.WalTimeType;

import javax.validation.constraints.NotNull;
import java.util.Set;

public final class ModifyOnboardTimeRequest {

    @NotNull(message = "변경할 시간대를 선택하세요")
    private final Set<WalTimeType> timeTypes;

    public ModifyOnboardTimeRequest(final Set<WalTimeType> timeTypes) {
        this.timeTypes = timeTypes;
    }

    public ModifyOnboardTimeRequestDto toServiceDto() {
        return new ModifyOnboardTimeRequestDto(timeTypes);
    }
}
