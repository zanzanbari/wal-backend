package backend.wal.user.application.port;

import backend.wal.user.application.port.dto.RegisterResignRequestDto;

public interface RegisterResignInfoUseCase {

    void register(RegisterResignRequestDto requestDto);
}
