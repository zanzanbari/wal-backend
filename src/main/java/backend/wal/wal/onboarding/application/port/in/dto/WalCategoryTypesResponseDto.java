package backend.wal.wal.onboarding.application.port.in.dto;

import backend.wal.wal.common.domain.WalCategoryType;

import java.util.Set;

public final class WalCategoryTypesResponseDto {

    private final Set<WalCategoryType> canceledCategoryTypes;
    private final Set<WalCategoryType> addedCategoryTypes;

    public WalCategoryTypesResponseDto(final Set<WalCategoryType> canceledCategoryTypes,
                                       final Set<WalCategoryType> addedCategoryTypes) {
        this.canceledCategoryTypes = canceledCategoryTypes;
        this.addedCategoryTypes = addedCategoryTypes;
    }

    public Set<WalCategoryType> getCanceledCategoryTypes() {
        return canceledCategoryTypes;
    }

    public Set<WalCategoryType> getAddedCategoryTypes() {
        return addedCategoryTypes;
    }
}
