package backend.wal.wal.onboarding.web.dto;

import backend.wal.wal.onboarding.application.port.in.dto.ModifyOnboardCategoryRequestDto;
import backend.wal.wal.common.domain.WalCategoryType;

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

    public Set<WalCategoryType> getCategoryTypes() {
        return categoryTypes;
    }
}
