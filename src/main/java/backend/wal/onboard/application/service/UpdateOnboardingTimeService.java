package backend.wal.onboard.application.service;

import backend.wal.onboard.application.port.UpdateOnboardingTimeUseCase;
import backend.wal.onboard.application.port.dto.ModifyOnboardTimeRequestDto;
import backend.wal.onboard.application.port.dto.WalTimeTypesResponseDto;
import backend.wal.onboard.domain.onboarding.service.OnboardingService;
import backend.wal.onboard.domain.service.WalSettingService;
import backend.wal.onboard.domain.common.WalTimeTypes;
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
