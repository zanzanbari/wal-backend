package backend.wal.admin.application.port.in;

public class AdminLoginRequestDto {

    private final String email;
    private final String password;

    public AdminLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
