package backend.wal.onboarding.app.dto.request;

import backend.wal.onboarding.domain.entity.WalCategoryType;

import java.util.Set;

public final class ModifyOnboardCategoryRequest {

    private final Set<WalCategoryType> categoryTypes;

    public ModifyOnboardCategoryRequest(final Set<WalCategoryType> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }

    public ModifyOnboardCategoryRequestDto toServiceDto() {
        return new ModifyOnboardCategoryRequestDto(categoryTypes);
    }
}
