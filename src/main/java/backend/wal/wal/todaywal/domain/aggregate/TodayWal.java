package backend.wal.onboard.todaywal.domain.aggregate;

import backend.wal.onboard.common.WalCategoryType;
import backend.wal.onboard.common.WalTimeType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TodayWal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalCategoryType categoryType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalTimeType timeType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowStatus showStatus;

    @Builder
    private TodayWal(final Long userId, final String message, final WalCategoryType categoryType, final WalTimeType timeType) {
        this.userId = userId;
        this.message = message;
        this.categoryType = categoryType;
        this.timeType = timeType;
        this.showStatus = ShowStatus.CLOSED;
    }

    public void updateShowStatus() {
        this.showStatus = ShowStatus.OPEN;
    }
}
