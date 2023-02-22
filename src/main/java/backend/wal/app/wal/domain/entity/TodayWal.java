package backend.wal.app.wal.domain.entity;

import backend.wal.app.onboarding.domain.entity.WalCategoryType;
import backend.wal.app.onboarding.domain.entity.WalTimeType;
import backend.wal.app.reservation.domain.entity.ShowStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
