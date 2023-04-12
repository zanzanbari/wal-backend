package backend.wal.auth.application.port.in;

public interface AuthUseCase {

    LoginResponseDto login(LoginRequestDto requestDto);
}
