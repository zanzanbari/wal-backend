package backend.wal.wal.onboarding.application.port.in.dto;

import backend.wal.wal.common.domain.WalCategoryType;

import java.util.Set;

public final class ModifyOnboardCategoryRequestDto {

    private final Set<WalCategoryType> categoryTypes;

    public ModifyOnboardCategoryRequestDto(final Set<WalCategoryType> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }

    public Set<WalCategoryType> getCategoryTypes() {
        return categoryTypes;
    }
}
