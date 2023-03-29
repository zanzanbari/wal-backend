package backend.wal.onboard.onboarding.application.service;

import backend.wal.onboard.onboarding.application.port.dto.InitOnboardInfoRequestDto;
import backend.wal.onboard.onboarding.application.port.RegisterOnboardingUseCase;
import backend.wal.onboard.nextwal.domain.NextWals;
import backend.wal.onboard.onboarding.domain.service.OnboardingService;
import backend.wal.onboard.service.WalSettingService;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class RegisterOnboardingService implements RegisterOnboardingUseCase {

    private final OnboardingService onboardingService;
    private final WalSettingService walSettingService;

    public RegisterOnboardingService(final OnboardingService onboardingService,
                                     final WalSettingService walSettingService) {
        this.onboardingService = onboardingService;
        this.walSettingService = walSettingService;
    }

    @Override
    @Transactional
    public void registerOnboardInfo(InitOnboardInfoRequestDto requestDto, Long userId) {
        onboardingService.register(requestDto.toOnboardingEntity(userId));
        NextWals setNextWals = walSettingService.setNextWals(requestDto.getCategoryTypes(), userId);
        walSettingService.setTodayWals(requestDto.getTimeTypes(), userId, setNextWals);
    }
}
