package backend.wal.wal.onboarding.domain.service;

import backend.wal.support.annotation.DomainService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.onboarding.application.port.in.dto.InitOnboardInfoRequestDto;
import backend.wal.wal.onboarding.application.port.in.dto.WalCategoryTypesResponseDto;
import backend.wal.wal.onboarding.application.port.in.dto.WalTimeTypesResponseDto;
import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.onboarding.domain.repository.OnboardingRepository;
import backend.wal.wal.todaywal.domain.WalTimeTypes;

import java.util.Set;

@DomainService
public class OnboardingDomainService {

    private final OnboardingRepository onboardingRepository;

    public OnboardingDomainService(final OnboardingRepository onboardingRepository) {
        this.onboardingRepository = onboardingRepository;
    }

    public void register(InitOnboardInfoRequestDto requestDto, Long userId) {
        OnboardingServiceUtils.validateAlreadyExistOnboardingByUserId(onboardingRepository, userId);
        Onboarding onboarding = Onboarding.newInstance(userId);
        onboarding.addCategories(requestDto.getCategoryTypes());
        onboarding.addTimes(requestDto.getTimeTypes());
        onboardingRepository.save(onboarding);
    }

    public WalTimeTypesResponseDto updateTimeTypes(Set<WalTimeType> modifiedTimeTypes, Long userId) {
        Onboarding onboarding = OnboardingServiceUtils.findExistOnboardingByUserId(onboardingRepository, userId);

        Set<WalTimeType> canceledTimeTypes = onboarding.extractCancelTimeTypes(modifiedTimeTypes);
        Set<WalTimeType> remainAfterCancel = onboarding.removeCanceledTimeTypes(modifiedTimeTypes);
        Set<WalTimeType> addedTimeTypes = onboarding.extractAddTimeTypes(modifiedTimeTypes, remainAfterCancel);
        onboarding.addTimes(addedTimeTypes);

        WalTimeTypes willCancelAfterNow = WalTimeTypes.createCompareAfterNow(canceledTimeTypes);
        WalTimeTypes willAddAfterNow = WalTimeTypes.createCompareAfterNow(addedTimeTypes);

        return new WalTimeTypesResponseDto(willCancelAfterNow.getValues(), willAddAfterNow.getValues());
    }

    public WalCategoryTypesResponseDto updateCategoryTypes(Set<WalCategoryType> modifiedCategoryTypes, Long userId) {
        Onboarding onboarding = OnboardingServiceUtils.findExistOnboardingByUserId(onboardingRepository, userId);

        Set<WalCategoryType> canceledCategoryTypes = onboarding.extractCancelCategoryTypes(modifiedCategoryTypes);
        Set<WalCategoryType> remainAfterCancel = onboarding.removeCanceledCategoryTypes(modifiedCategoryTypes);
        Set<WalCategoryType> addedCategoryTypes = onboarding.extractAddCategoryTypes(modifiedCategoryTypes, remainAfterCancel);
        onboarding.addCategories(addedCategoryTypes);

        return new WalCategoryTypesResponseDto(canceledCategoryTypes, addedCategoryTypes);
    }
}
