package backend.wal.auth.application.port;

import backend.wal.auth.application.port.dto.LoginRequestDto;

public interface AuthUseCase {

    Long login(LoginRequestDto requestDto);
}
