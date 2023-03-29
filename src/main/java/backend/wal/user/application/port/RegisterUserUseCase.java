package backend.wal.user.application.port;

import backend.wal.user.application.port.dto.CreateUserDto;

public interface RegisterUserUseCase {

    Long signup(CreateUserDto createDto);
}
