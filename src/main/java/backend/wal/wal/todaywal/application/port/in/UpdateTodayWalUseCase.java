package backend.wal.wal.todaywal.application.port.in;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;

import java.util.Set;

public interface UpdateTodayWalUseCase {

    void updateShowStatus(Long todayWalId, Long userId);

    Set<WalTimeType> updateTodayWalByCanceledCategoryTypesAndUserId(Set<WalCategoryType> canceledCategoryTypes,
                                                                    Long userId);

    void deleteTodayWalsByWillCancelAfterNow(Set<WalTimeType> willCancelAfterNow, Long userId);
}
