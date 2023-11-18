package backend.wal.admin.application.port.out;

import backend.wal.wal.common.domain.WalCategoryType;

import java.util.Set;

public interface RegisterItemManagePort {

    void registerItemsBy(WalCategoryType categoryType);

    void registerAllItems(Set<String> contents, WalCategoryType categoryType);
}
