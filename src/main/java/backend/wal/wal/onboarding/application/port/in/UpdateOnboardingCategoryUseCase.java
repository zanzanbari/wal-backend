package backend.wal.wal.onboarding.application.port.in;

import backend.wal.wal.onboarding.application.port.in.dto.ModifyOnboardCategoryRequestDto;

public interface UpdateOnboardingCategoryUseCase {

    void updateOnboardCategoryInfo(ModifyOnboardCategoryRequestDto requestDto, Long userId);
}
