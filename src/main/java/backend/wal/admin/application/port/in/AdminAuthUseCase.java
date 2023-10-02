package backend.wal.admin.application.port.in;

public interface AdminAuthUseCase {

    void create(AdminLoginRequestDto requestDto);

    Long login(AdminLoginRequestDto requestDto);
}
