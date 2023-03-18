package backend.wal.home.domain.entity;

import backend.wal.home.domain.vo.OpenStatus;
import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.reservation.domain.aggregate.vo.ShowStatus;
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
    private OpenStatus openStatus;

    public Home(final Long todayWalId, final WalTimeType timeType, final WalCategoryType categoryType,
                final String message, final ShowStatus showStatus) {
        this.todayWalId = todayWalId;
        this.timeType = timeType;
        this.categoryType = categoryType;
        this.message = message;
        this.showStatus = showStatus;
        this.openStatus = OpenStatus.UNABLE;
    }

    public void setOpenStatusBy(LocalDateTime now) {
        if (!timeType.isAfterNow(now)) {
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
