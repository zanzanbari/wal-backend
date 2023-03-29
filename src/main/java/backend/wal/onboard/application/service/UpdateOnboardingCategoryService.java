package backend.wal.onboard.application.service;

import backend.wal.onboard.application.port.UpdateOnboardingCategoryUseCase;
import backend.wal.onboard.application.port.dto.ModifyOnboardCategoryRequestDto;
import backend.wal.onboard.application.port.dto.WalCategoryTypesResponseDto;
import backend.wal.onboard.domain.common.WalTimeType;
import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.onboarding.service.OnboardingService;
import backend.wal.onboard.domain.service.WalSettingService;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@AppService
public class UpdateOnboardingCategoryService implements UpdateOnboardingCategoryUseCase {

    private final OnboardingService onboardingService;
    private final WalSettingService walSettingService;

    public UpdateOnboardingCategoryService(final OnboardingService onboardingService,
                                           final WalSettingService walSettingService) {
        this.onboardingService = onboardingService;
        this.walSettingService = walSettingService;
    }

    @Override
    @Transactional
    public void updateOnboardCategoryInfo(ModifyOnboardCategoryRequestDto requestDto, Long userId) {
        WalCategoryTypesResponseDto responseDto = onboardingService.updateCategoryTypes(requestDto.getCategoryTypes(), userId);
        updateWalCategoryInfo(responseDto.getCanceledCategoryTypes(), responseDto.getAddedCategoryTypes(), userId);
    }

    private void updateWalCategoryInfo(Set<WalCategoryType> canceledCategoryTypes,
                                       Set<WalCategoryType> addedCategoryTypes, Long userId) {
        Set<WalTimeType> willCancelAfterNow = new HashSet<>();
        if (!canceledCategoryTypes.isEmpty()) {
            willCancelAfterNow = walSettingService.updateWalInfoByCancelCategoryTypes(canceledCategoryTypes, userId);
        }
        if (!addedCategoryTypes.isEmpty()) {
            walSettingService.updateWalInfoByAddCategoryTypesInEmptyTimeTypes(willCancelAfterNow, addedCategoryTypes, userId);
        }
    }
}
