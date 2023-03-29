package backend.wal.onboard.application.port;

import backend.wal.onboard.application.port.dto.ModifyOnboardTimeRequestDto;

public interface UpdateOnboardingTimeUseCase {

    void updateOnboardTimeInfo(ModifyOnboardTimeRequestDto requestDto, Long userId);
}
