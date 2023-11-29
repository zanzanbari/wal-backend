package backend.wal.wal.nextwal.domain;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.domain.Item;
import backend.wal.wal.nextwal.adapter.out.persistence.NextWalEntity;
import backend.wal.wal.nextwal.adapter.out.persistence.NextWalAndItem;

public class NextWal {

    private final Long id;
    private final Long userId;
    private final WalCategoryType categoryType;
    private Item item;

    public NextWal(Long id, Long userId, WalCategoryType categoryType, Item item) {
        this.id = id;
        this.userId = userId;
        this.categoryType = categoryType;
        this.item = item;
    }

    public static NextWal of(NextWalEntity nextWalEntity, Item item) {
        return new NextWal(
                nextWalEntity.getId(),
                nextWalEntity.getUserId(),
                nextWalEntity.getCategoryType(),
                item
        );
    }

    public static NextWal create(NextWalAndItem.NextWalAttributes nextWalAttributes,
                                 WalCategoryType categoryType,
                                 Item item) {
        return new NextWal(
                nextWalAttributes.getId(),
                nextWalAttributes.getUserId(),
                categoryType,
                item
        );
    }

    public void updateItem(Item nextItem) {
        this.item = nextItem;
    }

    public Long getId() {
        return id;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }

    public Integer getCurrentCategoryItemNumber() {
        return item.getCategoryItemNumber();
    }

    public String getItemContents() {
        return item.getContents();
    }
}
