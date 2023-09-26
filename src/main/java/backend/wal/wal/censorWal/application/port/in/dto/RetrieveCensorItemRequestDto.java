package backend.wal.wal.censorWal.application.port.in.dto;

import backend.wal.wal.common.domain.WalCategoryType;

public class RetrieveCensorItemRequestDto {

    private final WalCategoryType categoryType;

    public RetrieveCensorItemRequestDto(WalCategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }
}
