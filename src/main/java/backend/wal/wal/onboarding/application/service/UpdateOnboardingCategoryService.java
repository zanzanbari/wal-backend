package backend.wal.wal.onboarding.application.service;

import backend.wal.wal.onboarding.application.port.in.UpdateOnboardingCategoryUseCase;
import backend.wal.wal.onboarding.application.port.out.WalSettingPort;
import backend.wal.wal.onboarding.application.port.in.dto.ModifyOnboardCategoryRequestDto;
import backend.wal.wal.onboarding.application.port.in.dto.WalCategoryTypesResponseDto;
import backend.wal.wal.onboarding.domain.service.OnboardingDomainService;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@AppService
public class UpdateOnboardingCategoryService implements UpdateOnboardingCategoryUseCase {

    private final OnboardingDomainService onboardingDomainService;
    private final WalSettingPort walSettingPort;

    public UpdateOnboardingCategoryService(final OnboardingDomainService onboardingDomainService,
                                           final WalSettingPort walSettingPort) {
        this.onboardingDomainService = onboardingDomainService;
        this.walSettingPort = walSettingPort;
    }

    @Override
    @Transactional
    public void updateOnboardCategoryInfo(ModifyOnboardCategoryRequestDto requestDto, Long userId) {
        WalCategoryTypesResponseDto responseDto = onboardingDomainService.updateCategoryTypes(requestDto.getCategoryTypes(), userId);
        updateWalCategoryInfo(responseDto.getCanceledCategoryTypes(), responseDto.getAddedCategoryTypes(), userId);
    }

    private void updateWalCategoryInfo(Set<WalCategoryType> canceledCategoryTypes,
                                       Set<WalCategoryType> addedCategoryTypes, Long userId) {
        Set<WalTimeType> willCancelAfterNow = new HashSet<>();
        if (!canceledCategoryTypes.isEmpty()) {
            willCancelAfterNow = walSettingPort.updateWalInfoByCancelCategoryTypes(canceledCategoryTypes, userId);
        }
        if (!addedCategoryTypes.isEmpty()) {
            walSettingPort.updateWalInfoByAddCategoryTypesInEmptyTimeTypes(willCancelAfterNow, addedCategoryTypes, userId);
        }
    }
}
