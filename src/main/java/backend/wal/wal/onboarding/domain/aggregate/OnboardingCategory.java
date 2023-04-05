package backend.wal.wal.onboarding.domain.aggregate;

import backend.wal.wal.common.domain.WalCategoryType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id")
    private Onboarding onboarding;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WalCategoryType categoryType;

    private OnboardingCategory(final Onboarding onboarding, final WalCategoryType categoryType) {
        this.onboarding = onboarding;
        this.categoryType = categoryType;
    }

    public static OnboardingCategory newInstance(final Onboarding onboarding, final WalCategoryType categoryType) {
        return new OnboardingCategory(onboarding, categoryType);
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }
}
