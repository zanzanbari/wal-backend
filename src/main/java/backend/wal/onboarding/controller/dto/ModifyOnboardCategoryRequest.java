package backend.wal.onboarding.controller.dto;

import backend.wal.onboarding.app.dto.request.ModifyOnboardCategoryRequestDto;
import backend.wal.onboarding.domain.entity.WalCategoryType;
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
