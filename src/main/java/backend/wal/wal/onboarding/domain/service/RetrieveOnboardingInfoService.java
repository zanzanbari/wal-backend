package backend.wal.wal.onboarding.domain.service;

import backend.wal.wal.onboarding.application.port.in.RetrieveOnboardingInfoUseCase;
import backend.wal.wal.onboarding.domain.repository.OnboardingRepository;
import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.support.annotation.DomainService;

import java.util.Set;

@DomainService
public class RetrieveOnboardingInfoService implements RetrieveOnboardingInfoUseCase {

    private final OnboardingRepository onboardingRepository;

    public RetrieveOnboardingInfoService(final OnboardingRepository onboardingRepository) {
        this.onboardingRepository = onboardingRepository;
    }

    @Override
    public Set<WalTimeType> retrieveTimeTypes(Long userId) {
        Onboarding onboarding = OnboardingServiceUtils.findExistOnboardingByUserId(onboardingRepository, userId);
        return onboarding.getTimeTypes();
    }

    @Override
    public Set<WalCategoryType> retrieveCategoryTypes(Long userId) {
        Onboarding onboarding = OnboardingServiceUtils.findExistOnboardingByUserId(onboardingRepository, userId);
        return onboarding.getCategoryTypes();
    }
}
