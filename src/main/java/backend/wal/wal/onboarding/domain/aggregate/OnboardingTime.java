package backend.wal.wal.onboarding.domain.aggregate;

import backend.wal.wal.common.domain.WalTimeType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id")
    private Onboarding onboarding;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WalTimeType timeType;

    private OnboardingTime(final Onboarding onboarding, final WalTimeType timeType) {
        this.onboarding = onboarding;
        this.timeType = timeType;
    }

    public static OnboardingTime newInstance(final Onboarding onboarding, final WalTimeType timeType) {
        return new OnboardingTime(onboarding, timeType);
    }

    public WalTimeType getTimeType() {
        return timeType;
    }
}
