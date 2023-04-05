package backend.wal.wal.onboarding.web.dto;

import backend.wal.wal.onboarding.application.port.in.dto.ModifyOnboardTimeRequestDto;
import backend.wal.wal.common.domain.WalTimeType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyOnboardTimeRequest {

    @NotNull(message = "변경할 시간대를 선택하세요")
    private Set<WalTimeType> timeTypes;

    public ModifyOnboardTimeRequest(final Set<WalTimeType> timeTypes) {
        this.timeTypes = timeTypes;
    }

    public ModifyOnboardTimeRequestDto toServiceDto() {
        return new ModifyOnboardTimeRequestDto(timeTypes);
    }
}
