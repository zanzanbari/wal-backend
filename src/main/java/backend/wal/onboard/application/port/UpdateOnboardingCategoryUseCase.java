package backend.wal.onboard.application.port;

import backend.wal.onboard.application.port.dto.ModifyOnboardCategoryRequestDto;

public interface UpdateOnboardingCategoryUseCase {

    void updateOnboardCategoryInfo(ModifyOnboardCategoryRequestDto requestDto, Long userId);
}
