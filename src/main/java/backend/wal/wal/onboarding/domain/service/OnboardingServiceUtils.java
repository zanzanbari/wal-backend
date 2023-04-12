package backend.wal.wal.onboarding.domain.service;

import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.onboarding.domain.repository.OnboardingRepository;
import backend.wal.wal.onboarding.exception.NotFoundOnboardingException;

public final class OnboardingServiceUtils {

    private OnboardingServiceUtils() throws InstantiationException {
        throw new InstantiationException("Block Instantiation");
    }

    static Onboarding findExistOnboardingByUserId(OnboardingRepository onboardingRepository, Long userId) {
        return onboardingRepository.findByUserId(userId)
                .orElseThrow(() -> NotFoundOnboardingException.notExists(userId));
    }
}
