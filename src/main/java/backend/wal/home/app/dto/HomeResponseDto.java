package backend.wal.home.app.dto;

import backend.wal.home.controller.dto.HomeResponse;
import backend.wal.home.domain.entity.Home;
import backend.wal.home.domain.vo.OpenStatus;
import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.reservation.domain.aggregate.vo.ShowStatus;

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

    public HomeResponse toWebResponse() {
        return new HomeResponse(
                todayWalId,
                timeType,
                categoryType,
                message,
                showStatus,
                openStatus
        );
    }
}
