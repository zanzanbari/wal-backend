package backend.wal.wal.onboarding.application.port.in;

import backend.wal.wal.onboarding.application.port.in.dto.ModifyOnboardTimeRequestDto;

public interface UpdateOnboardingTimeUseCase {

    void updateOnboardTimeInfo(ModifyOnboardTimeRequestDto requestDto, Long userId);
}
