package backend.wal.wal.todaywal.domain.view;

import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.aggregate.ShowStatus;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;

import com.google.common.base.Objects;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class Home {

    private final Long todayWalId;
    private final WalTimeType timeType;
    private final WalCategoryType categoryType;
    private final String message;
    private final ShowStatus showStatus;
    private final LocalDateTime sendTime;
    private OpenStatus openStatus;

    public Home(final Long todayWalId, final WalTimeType timeType, final WalCategoryType categoryType, final String message,
                final ShowStatus showStatus, final LocalDateTime sendTime) {
        this.todayWalId = todayWalId;
        this.timeType = timeType;
        this.categoryType = categoryType;
        this.message = message;
        this.showStatus = showStatus;
        this.sendTime = sendTime;
        this.openStatus = OpenStatus.UNABLE;
    }

    public static Home of(final TodayWal todayWal) {
        return new Home(
                todayWal.getId(),
                todayWal.getTimeType(),
                todayWal.getCategoryType(),
                todayWal.getMessage(),
                todayWal.getShowStatus(),
                todayWal.getSendTime()
        );
    }

    public void setOpenStatusBy(LocalDateTime now) {
        if (sendTime.isBefore(now)) {
            this.openStatus = OpenStatus.ABLE;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home home = (Home) o;
        return Objects.equal(todayWalId, home.todayWalId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(todayWalId);
    }
}
