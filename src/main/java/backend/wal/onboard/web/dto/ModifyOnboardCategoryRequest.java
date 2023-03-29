package backend.wal.onboard.web.dto;

import backend.wal.onboard.application.port.dto.ModifyOnboardCategoryRequestDto;
import backend.wal.onboard.domain.common.WalCategoryType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyOnboardCategoryRequest {

    private Set<WalCategoryType> categoryTypes;

    public ModifyOnboardCategoryRequest(final Set<WalCategoryType> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }

    public ModifyOnboardCategoryRequestDto toServiceDto() {
        return new ModifyOnboardCategoryRequestDto(categoryTypes);
    }
}
