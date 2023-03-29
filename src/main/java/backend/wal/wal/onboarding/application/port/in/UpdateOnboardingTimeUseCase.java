package backend.wal.wal.onboarding.application.port;

import backend.wal.wal.onboarding.application.port.dto.ModifyOnboardTimeRequestDto;

public interface UpdateOnboardingTimeUseCase {

    void updateOnboardTimeInfo(ModifyOnboardTimeRequestDto requestDto, Long userId);
}
