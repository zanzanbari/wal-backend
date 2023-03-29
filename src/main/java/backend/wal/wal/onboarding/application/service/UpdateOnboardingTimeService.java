package backend.wal.onboard.onboarding.application.service;

import backend.wal.onboard.onboarding.application.port.UpdateOnboardingTimeUseCase;
import backend.wal.onboard.onboarding.application.port.dto.ModifyOnboardTimeRequestDto;
import backend.wal.onboard.onboarding.application.port.dto.WalTimeTypesResponseDto;
import backend.wal.onboard.onboarding.domain.service.OnboardingService;
import backend.wal.onboard.service.WalSettingService;
import backend.wal.onboard.common.WalTimeTypes;
import backend.wal.support.annotation.AppService;

@AppService
public class UpdateOnboardingTimeService implements UpdateOnboardingTimeUseCase {

    private final OnboardingService onboardingService;
    private final WalSettingService walSettingService;

    public UpdateOnboardingTimeService(final OnboardingService onboardingService,
                                       final WalSettingService walSettingService) {
        this.onboardingService = onboardingService;
        this.walSettingService = walSettingService;
    }

    @Override
    public void updateOnboardTimeInfo(ModifyOnboardTimeRequestDto requestDto, Long userId) {
        WalTimeTypesResponseDto responseDto = onboardingService.updateTimeTypes(requestDto.getTimeTypes(), userId);
        updateWalInfoByCancelAfterNow(responseDto.getWillCancelAfterNow(), userId);
        updateWalInfoByAddAfterNow(responseDto.getWillAddAfterNow(), userId);
    }

    private void updateWalInfoByCancelAfterNow(WalTimeTypes willCancelAfterNow, Long userId) {
        if (willCancelAfterNow.isExist()) {
            walSettingService.updateTodayWalByCancelTimeTypes(willCancelAfterNow, userId);
        }
    }

    private void updateWalInfoByAddAfterNow(WalTimeTypes willAddAfterNow, Long userId) {
        if (willAddAfterNow.isExist()) {
            walSettingService.updateTodayWalByAddTimeTypes(willAddAfterNow, userId);
        }
    }
}
