package backend.wal.wal.onboarding.application.port;

import backend.wal.wal.onboarding.application.port.dto.InitOnboardInfoRequestDto;

public interface RegisterOnboardingUseCase {

    void registerOnboardInfo(InitOnboardInfoRequestDto requestDto, Long userId);
}
