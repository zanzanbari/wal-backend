package backend.wal.wal.todaywal.domain.aggregate;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

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

    private LocalDateTime sendTime;

    @Builder
    private TodayWal(final Long userId, final String message, final WalCategoryType categoryType,
                     final WalTimeType timeType, final LocalDateTime sendTime) {
        this.userId = userId;
        this.message = message;
        this.categoryType = categoryType;
        this.timeType = timeType;
        this.showStatus = ShowStatus.CLOSED;
        this.sendTime = sendTime;
    }

    public void updateShowStatus() {
        this.showStatus = ShowStatus.OPEN;
    }
}
