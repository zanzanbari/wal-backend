package backend.wal.admin.web.dto;

public class AdminLoginResponse {

    private final Long expiredIn;

    public AdminLoginResponse(Long expiredIn) {
        this.expiredIn = expiredIn;
    }

    public Long getExpiredIn() {
        return expiredIn;
    }
}
