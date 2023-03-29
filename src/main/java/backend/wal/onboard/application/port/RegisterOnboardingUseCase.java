package backend.wal.onboard.application.port;

import backend.wal.onboard.application.port.dto.InitOnboardInfoRequestDto;

public interface RegisterOnboardingUseCase {

    void registerOnboardInfo(InitOnboardInfoRequestDto requestDto, Long userId);
}
