package backend.wal.admin.application.port.in;

public class AdminLoginResponseDto {

    private final Long userId;
    private final String role;

    public AdminLoginResponseDto(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
