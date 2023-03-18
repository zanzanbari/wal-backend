package backend.wal.home.controller.dto;

import backend.wal.home.domain.vo.OpenStatus;
import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.reservation.domain.aggregate.vo.ShowStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HomeResponse {

    private Long todayWalId;
    private WalTimeType timeType;
    private WalCategoryType categoryType;
    private String message;
    private ShowStatus showStatus;
    private OpenStatus openStatus;

    public HomeResponse(final Long todayWalId, final WalTimeType timeType, final WalCategoryType categoryType,
                        final String message, final ShowStatus showStatus, final OpenStatus openStatus) {
        this.todayWalId = todayWalId;
        this.timeType = timeType;
        this.categoryType = categoryType;
        this.message = message;
        this.showStatus = showStatus;
        this.openStatus = openStatus;
    }
}
