package backend.wal.onboard.application.port.dto;

import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.onboarding.aggregate.Onboarding;
import backend.wal.onboard.domain.common.WalTimeType;
import lombok.Getter;

import java.util.Set;

@Getter
public final class InitOnboardInfoRequestDto {

    private final String nickname;
    private final Set<WalCategoryType> categoryTypes;
    private final Set<WalTimeType> timeTypes;

    public InitOnboardInfoRequestDto(final String nickname, final Set<WalCategoryType> categoryTypes,
                                     final Set<WalTimeType> timeTypes) {
        this.nickname = nickname;
        this.categoryTypes = categoryTypes;
        this.timeTypes = timeTypes;
    }

    public Onboarding toOnboardingEntity(Long userId) {
        Onboarding onboarding = Onboarding.newInstance(userId);
        onboarding.addCategories(categoryTypes);
        onboarding.addTimes(timeTypes);
        return onboarding;
    }
}
