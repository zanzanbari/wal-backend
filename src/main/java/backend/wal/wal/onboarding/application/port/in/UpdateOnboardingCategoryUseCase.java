package backend.wal.wal.onboarding.application.port;

import backend.wal.wal.onboarding.application.port.dto.ModifyOnboardCategoryRequestDto;

public interface UpdateOnboardingCategoryUseCase {

    void updateOnboardCategoryInfo(ModifyOnboardCategoryRequestDto requestDto, Long userId);
}
