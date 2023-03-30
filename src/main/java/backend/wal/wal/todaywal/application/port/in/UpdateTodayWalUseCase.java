package backend.wal.wal.todaywal.application.port;

import backend.wal.wal.common.domain.WalCategoryType;

import java.util.Set;

public interface UpdateTodayWalUseCase {

    void updateShowStatus(Long todayWalId, Long userId);

    void updateTodayWalByCanceledCategoryTypesAndUserId(Set<WalCategoryType> canceledCategoryTypes, Long userId);
}
