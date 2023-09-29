package backend.wal.wal.item.application.port.in;

import backend.wal.wal.common.domain.WalCategoryType;

public interface CountItemUseCase {

    Long countAllCorrespondItemsByCategoryType(WalCategoryType categoryType);
}
