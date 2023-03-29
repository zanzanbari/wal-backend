package backend.wal.onboard.application.port.dto;

import backend.wal.onboard.domain.common.WalCategoryType;
import lombok.Getter;

import java.util.Set;

@Getter
public final class ModifyOnboardCategoryRequestDto {

    private final Set<WalCategoryType> categoryTypes;

    public ModifyOnboardCategoryRequestDto(final Set<WalCategoryType> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }
}
