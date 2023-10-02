package backend.wal.admin.application.port.out;

public interface AdminJwtManagePort {
    AdminTokenResponseDto createToken(Long adminId);
}
