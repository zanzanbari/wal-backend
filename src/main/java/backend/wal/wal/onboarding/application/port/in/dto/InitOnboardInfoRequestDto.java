package backend.wal.wal.onboarding.application.port.in.dto;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.common.domain.WalTimeType;
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