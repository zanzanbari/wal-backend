package backend.wal.wal.item.domain;

import backend.wal.wal.common.domain.WalCategoryType;

public class Category {

    private final WalCategoryType categoryType;

    public Category(WalCategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }
}
