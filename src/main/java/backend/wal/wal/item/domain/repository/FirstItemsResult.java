package backend.wal.wal.item.domain.repository;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.aggregate.Item;

public class FirstItemsResult {

    private final Item firstItem;
    private final WalCategoryType categoryType;

    public FirstItemsResult(Item firstItem, WalCategoryType categoryType) {
        this.firstItem = firstItem;
        this.categoryType = categoryType;
    }

    public Item getFirstItem() {
        return firstItem;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }
}
