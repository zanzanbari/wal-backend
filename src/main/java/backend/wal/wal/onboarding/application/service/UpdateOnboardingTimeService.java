package backend.wal.wal.onboarding.application.service;

import backend.wal.wal.onboarding.application.port.in.dto.ModifyOnboardTimeRequestDto;
import backend.wal.wal.onboarding.application.port.in.dto.WalTimeTypesResponseDto;
import backend.wal.wal.onboarding.application.port.in.UpdateOnboardingTimeUseCase;
import backend.wal.wal.onboarding.application.port.out.WalSettingPort;
import backend.wal.wal.onboarding.domain.service.OnboardingDomainService;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AppService
public class UpdateOnboardingTimeService implements UpdateOnboardingTimeUseCase {

    private final OnboardingDomainService onboardingDomainService;
    private final WalSettingPort walSettingPort;

    public UpdateOnboardingTimeService(final OnboardingDomainService onboardingDomainService,
                                       final WalSettingPort walSettingPort) {
        this.onboardingDomainService = onboardingDomainService;
        this.walSettingPort = walSettingPort;
    }

    @Override
    @Transactional
    public void updateOnboardTimeInfo(ModifyOnboardTimeRequestDto requestDto, Long userId) {
        WalTimeTypesResponseDto responseDto = onboardingDomainService.updateTimeTypes(requestDto.getTimeTypes(), userId);
        updateWalInfoByCancelAfterNow(responseDto.getWillCancelAfterNow(), userId);
        updateWalInfoByAddAfterNow(responseDto.getWillAddAfterNow(), userId);
    }

    private void updateWalInfoByCancelAfterNow(Set<WalTimeType> willCancelAfterNow, Long userId) {
        if (!willCancelAfterNow.isEmpty()) {
            walSettingPort.updateTodayWalByCancelTimeTypes(willCancelAfterNow, userId);
        }
    }

    private void updateWalInfoByAddAfterNow(Set<WalTimeType> willAddAfterNow, Long userId) {
        if (!willAddAfterNow.isEmpty()) {
            walSettingPort.updateTodayWalByAddTimeTypes(willAddAfterNow, userId);
        }
    }
}
