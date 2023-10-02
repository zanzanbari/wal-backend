package backend.wal.admin.application.port.out;

public class AdminTokenResponseDto {

    private final String token;
    private final Long expiredIn;

    public AdminTokenResponseDto(String token, Long expiredIn) {
        this.token = token;
        this.expiredIn = expiredIn;
    }

    public String getToken() {
        return token;
    }

    public Long getExpiredIn() {
        return expiredIn;
    }
}
