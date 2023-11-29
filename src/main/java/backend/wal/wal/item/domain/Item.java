package backend.wal.wal.item.domain;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.adapter.out.persistence.ItemEntity;
import backend.wal.wal.nextwal.adapter.out.persistence.NextWalAndItem;

public class Item {

    private final Long id;
    private final Category category;
    private final String contents;
    private final String imageUrl;
    private final Integer categoryItemNumber;

    private Item(Long id, Category category, String contents, String imageUrl, Integer categoryItemNumber) {
        this.id = id;
        this.category = category;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.categoryItemNumber = categoryItemNumber;
    }

    public static Item of(ItemEntity itemEntity, Category category) {
        return new Item(
                itemEntity.getId(),
                category,
                itemEntity.getContents(),
                itemEntity.getImageUrl(),
                itemEntity.getCategoryItemNumber()
        );
    }

    public static Item create(NextWalAndItem.ItemAttributes itemAttributes, WalCategoryType categoryType) {
        return new Item(
                itemAttributes.getId(),
                new Category(categoryType),
                itemAttributes.getContents(),
                itemAttributes.getImageUrl(),
                itemAttributes.getCategoryItemNumber()
        );
    }

    public Long getId() {
        return id;
    }

    public WalCategoryType getCategoryType() {
        return category.getCategoryType();
    }

    public String getContents() {
        return contents;
    }

    public Integer getCategoryItemNumber() {
        return categoryItemNumber;
    }
}
