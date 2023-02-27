package backend.wal.onboarding.domain.entity;

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
    private WalTimeType walTimeType;

    private OnboardingTime(final Onboarding onboarding, final WalTimeType walTimeType) {
        this.onboarding = onboarding;
        this.walTimeType = walTimeType;
    }

    public static OnboardingTime newInstance(final Onboarding onboarding, final WalTimeType walTimeType) {
        return new OnboardingTime(onboarding, walTimeType);
    }
}
