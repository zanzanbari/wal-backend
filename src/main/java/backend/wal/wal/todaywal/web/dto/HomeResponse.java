package backend.wal.wal.todaywal.web.dto;

import backend.wal.wal.todaywal.domain.view.OpenStatus;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.todaywal.domain.aggregate.ShowStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
