package backend.wal.wal.nextwal.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;

public class NextWalAndItem {

    private final NextWalAttributes nextWalAttributes;
    private final ItemAttributes itemAttributes;
    private final WalCategoryType categoryType;

    public NextWalAndItem(Long nextWalId, Long userId, WalCategoryType categoryType,
                          Long itemId, String contents, String imageUrl, Integer categoryItemNumber) {
        this.nextWalAttributes = new NextWalAttributes(nextWalId, userId);
        this.itemAttributes = new ItemAttributes(itemId, contents, imageUrl, categoryItemNumber);
        this.categoryType = categoryType;
    }

    public static class NextWalAttributes {

        private final Long id;
        private final Long userId;

        private NextWalAttributes(Long id, Long userId) {
            this.id = id;
            this.userId = userId;
        }

        public Long getId() {
            return id;
        }

        public Long getUserId() {
            return userId;
        }
    }

    public static class ItemAttributes {

        private final Long id;
        private final String contents;
        private final String imageUrl;
        private final Integer categoryItemNumber;

        private ItemAttributes(Long id, String contents, String imageUrl, Integer categoryItemNumber) {
            this.id = id;
            this.contents = contents;
            this.imageUrl = imageUrl;
            this.categoryItemNumber = categoryItemNumber;
        }

        public Long getId() {
            return id;
        }

        public String getContents() {
            return contents;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public Integer getCategoryItemNumber() {
            return categoryItemNumber;
        }
    }

    public NextWalAttributes getNextWalMapper() {
        return nextWalAttributes;
    }

    public ItemAttributes getItemMapper() {
        return itemAttributes;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }
}
