package backend.wal.wal.censorWal.application.port.in.dto;

import backend.wal.wal.common.domain.WalCategoryType;

public class ApprovedCensorItemResponseDto {

    private final WalCategoryType categoryType;
    private final String contents;
    private final String imageUrl;

    public ApprovedCensorItemResponseDto(WalCategoryType categoryType, String contents, String imageUrl) {
        this.categoryType = categoryType;
        this.contents = contents;
        this.imageUrl = imageUrl;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }

    public String getContents() {
        return contents;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
