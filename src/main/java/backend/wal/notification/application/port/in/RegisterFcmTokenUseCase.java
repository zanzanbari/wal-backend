package backend.wal.notification.application.port;

import backend.wal.auth.application.port.dto.InitFcmRequestDto;

public interface RegisterFcmTokenUseCase {

    void register(InitFcmRequestDto requestDto);
}
