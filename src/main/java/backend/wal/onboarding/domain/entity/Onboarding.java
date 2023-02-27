package backend.wal.app.onboarding.domain.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Onboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "onboarding_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "onboarding")
    private final List<OnboardingCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding")
    private final List<OnboardingTime> times = new ArrayList<>();

    private Onboarding(Long userId) {
        this.userId = userId;
    }

    public static Onboarding newInstance(Long userId) {
        return new Onboarding(userId);
    }

    public void addCategories(Set<WalCategoryType> categoryTypes) {
        for (WalCategoryType categoryType : categoryTypes) {
            addCategory(categoryType);
        }
    }

    private void addCategory(WalCategoryType categoryType) {
        OnboardingCategory onboardingCategory = OnboardingCategory.newInstance(this, categoryType);
        this.categories.add(onboardingCategory);
    }

    public void addTimes(Set<WalTimeType> timeTypes) {
        for (WalTimeType timeType : timeTypes) {
            addTime(timeType);
        }
    }

    private void addTime(WalTimeType timeType) {
        OnboardingTime onboardingTime = OnboardingTime.newInstance(this, timeType);
        this.times.add(onboardingTime);
    }
}
