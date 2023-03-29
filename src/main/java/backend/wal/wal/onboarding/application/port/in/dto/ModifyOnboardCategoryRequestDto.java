package backend.wal.wal.onboarding.application.port.dto;

import backend.wal.wal.common.domain.aggregate.WalCategoryType;
import lombok.Getter;

import java.util.Set;

@Getter
public final class ModifyOnboardCategoryRequestDto {

    private final Set<WalCategoryType> categoryTypes;

    public ModifyOnboardCategoryRequestDto(final Set<WalCategoryType> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }
}
