package backend.wal.user.adapter;

import backend.wal.user.application.port.out.OnboardingPort;
import backend.wal.user.application.port.out.CategoryTypeResponseDto;
import backend.wal.user.application.port.out.TimeTypeResponseDto;
import backend.wal.wal.onboarding.application.port.in.RetrieveOnboardingInfoUseCase;

import org.springframework.stereotype.Component;

@Component
public final class OnboardingAdapter implements OnboardingPort {

    private final RetrieveOnboardingInfoUseCase retrieveOnboardingInfoUseCase;

    public OnboardingAdapter(final RetrieveOnboardingInfoUseCase retrieveOnboardingInfoUseCase) {
        this.retrieveOnboardingInfoUseCase = retrieveOnboardingInfoUseCase;
    }

    @Override
    public TimeTypeResponseDto retrieveTimeTypes(Long userId) {
        return new TimeTypeResponseDto(retrieveOnboardingInfoUseCase.retrieveTimeTypes(userId));
    }

    @Override
    public CategoryTypeResponseDto retrieveCategoryTypes(Long userId) {
        return new CategoryTypeResponseDto(retrieveOnboardingInfoUseCase.retrieveCategoryTypes(userId));
    }
}
