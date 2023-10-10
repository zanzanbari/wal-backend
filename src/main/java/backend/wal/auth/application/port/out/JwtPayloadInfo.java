package backend.wal.auth.application.port.out;

public class JwtPayloadInfo {

    private final Long id;
    private final String role;

    public JwtPayloadInfo(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
