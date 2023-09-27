package backend.wal.admin.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;

public interface RegisterItemManagePort {
    void registerItemsBy(WalCategoryType categoryType);
}
