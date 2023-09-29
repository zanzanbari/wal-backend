package backend.wal.wal.censorWal.application.port.in.dto;

import backend.wal.wal.common.domain.WalCategoryType;

public class CreateCensorItemRequestDto {

    private final WalCategoryType categoryType;
    private final String contents;

    public CreateCensorItemRequestDto(WalCategoryType categoryType, String contents) {
        this.categoryType = categoryType;
        this.contents = contents;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }

    public String getContents() {
        return contents;
    }
}
