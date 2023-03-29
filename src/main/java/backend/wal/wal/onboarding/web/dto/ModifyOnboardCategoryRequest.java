package backend.wal.onboard.onboarding.web.dto;

import backend.wal.onboard.onboarding.application.port.dto.ModifyOnboardCategoryRequestDto;
import backend.wal.onboard.common.WalCategoryType;
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
