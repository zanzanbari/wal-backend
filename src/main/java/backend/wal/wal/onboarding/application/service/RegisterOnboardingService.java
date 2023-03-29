package backend.wal.wal.onboarding.application.service;

import backend.wal.wal.onboarding.application.port.in.dto.InitOnboardInfoRequestDto;
import backend.wal.wal.onboarding.application.port.in.RegisterOnboardingUseCase;
import backend.wal.wal.onboarding.application.port.out.WalSettingPort;
import backend.wal.wal.onboarding.domain.service.OnboardingDomainService;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class RegisterOnboardingService implements RegisterOnboardingUseCase {

    private final OnboardingDomainService onboardingDomainService;
    private final WalSettingPort walSettingPort;

    public RegisterOnboardingService(final OnboardingDomainService onboardingDomainService, final WalSettingPort walSettingPort) {
        this.onboardingDomainService = onboardingDomainService;
        this.walSettingPort = walSettingPort;
    }

    @Override
    @Transactional
    public void registerOnboardInfo(InitOnboardInfoRequestDto requestDto, Long userId) {
        onboardingDomainService.register(requestDto.toOnboardingEntity(userId));
        walSettingPort.setTodayWals(
                requestDto.getTimeTypes(),
                userId,
                walSettingPort.setNextWals(requestDto.getCategoryTypes(), userId)
        );
    }
}
