package backend.wal.onboard.application.service;

import backend.wal.onboard.application.port.dto.InitOnboardInfoRequestDto;
import backend.wal.onboard.application.port.RegisterOnboardingUseCase;
import backend.wal.onboard.domain.nextwal.NextWals;
import backend.wal.onboard.domain.onboarding.service.OnboardingService;
import backend.wal.onboard.domain.service.WalSettingService;
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
