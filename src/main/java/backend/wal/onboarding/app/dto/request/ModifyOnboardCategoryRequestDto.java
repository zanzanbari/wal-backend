package backend.wal.onboarding.app.dto.request;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import lombok.Getter;

import java.util.Set;

@Getter
public final class ModifyOnboardCategoryRequestDto {

    private final Set<WalCategoryType> categoryTypes;

    public ModifyOnboardCategoryRequestDto(final Set<WalCategoryType> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }
}
