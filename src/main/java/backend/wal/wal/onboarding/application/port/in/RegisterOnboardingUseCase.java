package backend.wal.wal.onboarding.application.port.in;

import backend.wal.wal.onboarding.application.port.in.dto.InitOnboardInfoRequestDto;

public interface RegisterOnboardingUseCase {

    void registerOnboardInfo(InitOnboardInfoRequestDto requestDto, Long userId);
}
