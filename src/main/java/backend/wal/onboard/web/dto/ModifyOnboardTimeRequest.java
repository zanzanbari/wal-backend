package backend.wal.onboard.web.dto;

import backend.wal.onboard.application.port.dto.ModifyOnboardTimeRequestDto;
import backend.wal.onboard.domain.common.WalTimeType;
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
