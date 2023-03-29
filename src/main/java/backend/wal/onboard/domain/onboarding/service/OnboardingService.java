package backend.wal.onboard.domain.onboarding.service;

import backend.wal.onboard.application.port.dto.WalCategoryTypesResponseDto;
import backend.wal.onboard.application.port.dto.WalTimeTypesResponseDto;
import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.common.WalTimeType;
import backend.wal.onboard.domain.onboarding.aggregate.Onboarding;
import backend.wal.onboard.domain.onboarding.repository.OnboardingRepository;
import backend.wal.onboard.domain.common.WalTimeTypes;
import backend.wal.onboard.exception.NotFoundOnboardingException;
import backend.wal.support.annotation.DomainService;

import java.util.Set;

@DomainService
public class OnboardingService {

    private final OnboardingRepository onboardingRepository;

    public OnboardingService(final OnboardingRepository onboardingRepository) {
        this.onboardingRepository = onboardingRepository;
    }

    public void register(Onboarding onboarding) {
        onboardingRepository.save(onboarding);
    }

    public WalTimeTypesResponseDto updateTimeTypes(Set<WalTimeType> modifiedTimeTypes, Long userId) { // 추가된놈, 삭제된 놈 반환
        Onboarding onboarding = findOnboardingByUserId(userId);
        Set<WalTimeType> canceledTimeTypes = onboarding.extractCancelTimeTypes(modifiedTimeTypes);
        Set<WalTimeType> remainAfterCancel = onboarding.removeCanceledTimeTypes(modifiedTimeTypes);
        Set<WalTimeType> addedTimeTypes = onboarding.extractAddTimeTypes(modifiedTimeTypes, remainAfterCancel);
        onboarding.addTimes(addedTimeTypes);

        WalTimeTypes willCancelAfterNow = WalTimeTypes.createCompareAfterNow(canceledTimeTypes);
        WalTimeTypes willAddAfterNow = WalTimeTypes.createCompareAfterNow(addedTimeTypes);

        return new WalTimeTypesResponseDto(willCancelAfterNow, willAddAfterNow);
    }

    public WalCategoryTypesResponseDto updateCategoryTypes(Set<WalCategoryType> modifiedCategoryTypes, Long userId) { // 추가된놈, 삭제된 놈 반환
        Onboarding onboarding = findOnboardingByUserId(userId);
        Set<WalCategoryType> canceledCategoryTypes = onboarding.extractCancelCategoryTypes(modifiedCategoryTypes);
        Set<WalCategoryType> remainAfterCancel = onboarding.removeCanceledCategoryTypes(modifiedCategoryTypes);
        Set<WalCategoryType> addedCategoryTypes = onboarding.extractAddCategoryTypes(modifiedCategoryTypes, remainAfterCancel);
        onboarding.addCategories(addedCategoryTypes);

        return new WalCategoryTypesResponseDto(canceledCategoryTypes, addedCategoryTypes);
    }

    public Onboarding findOnboardingByUserId(Long userId) {
        return onboardingRepository.findByUserId(userId)
                .orElseThrow(() -> NotFoundOnboardingException.notExists(userId));
    }
}
