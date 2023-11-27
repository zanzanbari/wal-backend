package backend.wal.wal.nextwal.adapter.out.persistence;

import backend.wal.wal.item.adapter.out.persistence.ItemEntity;

public class NextWalWithItem {

    private final NextWalEntity nextWalEntity;
    private final ItemEntity itemEntity;

    public NextWalWithItem(NextWalEntity nextWalEntity, ItemEntity itemEntity) {
        this.nextWalEntity = nextWalEntity;
        this.itemEntity = itemEntity;
    }

    public NextWalEntity getNextWalEntity() {
        return nextWalEntity;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }
}
