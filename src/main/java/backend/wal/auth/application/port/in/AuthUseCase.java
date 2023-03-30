package backend.wal.auth.application.port.in;

public interface AuthUseCase {

    Long login(LoginRequestDto requestDto);
}
