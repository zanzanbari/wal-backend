package backend.wal.admin.adapter;

import backend.wal.admin.application.port.out.PasswordManagePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordManageAdapter implements PasswordManagePort {

    private final PasswordEncoder passwordEncoder;

    public PasswordManageAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean isMatch(String requestPassword, String savedPassword) {
        return passwordEncoder.matches(requestPassword, savedPassword);
    }
}
