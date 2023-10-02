package backend.wal.admin.application.port.out;

public interface PasswordManagePort {

    String encode(String password);

    boolean isMatch(String requestPassword, String savedPassword);
}
