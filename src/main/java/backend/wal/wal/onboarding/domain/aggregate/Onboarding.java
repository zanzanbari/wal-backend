package backend.wal.onboard.onboarding.domain.aggregate;

import backend.wal.onboard.common.WalCategoryType;
import backend.wal.onboard.common.WalTimeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Onboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "onboarding_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.PERSIST)
    private final List<OnboardingCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.PERSIST)
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
        categories.add(onboardingCategory);
    }

    public void addTimes(Set<WalTimeType> timeTypes) {
        for (WalTimeType timeType : timeTypes) {
            addTime(timeType);
        }
    }

    private void addTime(WalTimeType timeType) {
        OnboardingTime onboardingTime = OnboardingTime.newInstance(this, timeType);
        times.add(onboardingTime);
    }

    public Set<WalTimeType> extractCancelTimeTypes(Set<WalTimeType> modifiedTimeTypes) {
        Set<WalTimeType> currentTimeTypes = getTimeTypes();
        return currentTimeTypes.stream()
                .filter(currentTimeType -> afterHasNotBefore(modifiedTimeTypes, currentTimeType))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Set<WalTimeType> removeCanceledTimeTypes(Set<WalTimeType> modifiedTimeTypes) {
        times.removeIf(currentOnboardingTime ->
                afterHasNotBefore(modifiedTimeTypes, currentOnboardingTime.getTimeType()));
        return getTimeTypes();
    }

    private Set<WalTimeType> getTimeTypes() {
        return times.stream()
                .map(OnboardingTime::getTimeType)
                .collect(Collectors.toSet());
    }

    @NotNull
    public Set<WalTimeType> extractAddTimeTypes(Set<WalTimeType> modifiedTimeTypes, Set<WalTimeType> remainAfterCancel) {
        return modifiedTimeTypes.stream()
                .filter(modifiedTimeType -> afterHasNotBefore(remainAfterCancel, modifiedTimeType))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private boolean afterHasNotBefore(Set<WalTimeType> afterTimeTypes, WalTimeType beforeTimeType) {
        return !afterTimeTypes.contains(beforeTimeType);
    }

    public Set<WalCategoryType> extractCancelCategoryTypes(Set<WalCategoryType> modifiedCategoryTypes) {
        Set<WalCategoryType> currentCategoryTypes = getCategoryTypes();
        return currentCategoryTypes.stream()
                .filter(currentCategoryType -> afterHasNotBefore(modifiedCategoryTypes, currentCategoryType))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Set<WalCategoryType> removeCanceledCategoryTypes(Set<WalCategoryType> modifiedCategoryTypes) {
        categories.removeIf(currentOnboardingCategory ->
                afterHasNotBefore(modifiedCategoryTypes, currentOnboardingCategory.getCategoryType()));
        return getCategoryTypes();
    }

    private Set<WalCategoryType> getCategoryTypes() {
        return categories.stream()
                .map(OnboardingCategory::getCategoryType)
                .collect(Collectors.toSet());
    }

    @NotNull
    public Set<WalCategoryType> extractAddCategoryTypes(Set<WalCategoryType> modifiedCategoryTypes,
                                                        Set<WalCategoryType> remainAfterCancel) {
        return modifiedCategoryTypes.stream()
                .filter(modifiedCategoryType -> afterHasNotBefore(remainAfterCancel, modifiedCategoryType))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private boolean afterHasNotBefore(Set<WalCategoryType> afterCategoryTypes, WalCategoryType beforeCategoryType) {
        return !afterCategoryTypes.contains(beforeCategoryType);
    }
}
