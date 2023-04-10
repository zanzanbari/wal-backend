package backend.wal.wal.todaywal.application.port.in;

import backend.wal.wal.todaywal.domain.view.Home;
import backend.wal.wal.todaywal.domain.aggregate.ShowStatus;
import backend.wal.wal.todaywal.domain.view.OpenStatus;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;

public final class HomeResponseDto {

    private final Long todayWalId;
    private final WalTimeType timeType;
    private final WalCategoryType categoryType;
    private final String message;
    private final ShowStatus showStatus;
    private final OpenStatus openStatus;

    private HomeResponseDto(final Long todayWalId, final WalTimeType timeType, final WalCategoryType categoryType,
                           final String message, final ShowStatus showStatus, final OpenStatus openStatus) {
        this.todayWalId = todayWalId;
        this.timeType = timeType;
        this.categoryType = categoryType;
        this.message = message;
        this.showStatus = showStatus;
        this.openStatus = openStatus;
    }

    public static HomeResponseDto from(final Home home) {
        return new HomeResponseDto(
                home.getTodayWalId(),
                home.getTimeType(),
                home.getCategoryType(),
                home.getMessage(),
                home.getShowStatus(),
                home.getOpenStatus()
        );
    }

    public Long getTodayWalId() {
        return todayWalId;
    }

    public WalTimeType getTimeType() {
        return timeType;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }

    public String getMessage() {
        return message;
    }

    public ShowStatus getShowStatus() {
        return showStatus;
    }

    public OpenStatus getOpenStatus() {
        return openStatus;
    }
}
