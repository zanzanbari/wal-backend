package backend.wal.user.application.port.in;

public interface RegisterUserUseCase {

    Long signup(CreateUserDto createDto);
}
