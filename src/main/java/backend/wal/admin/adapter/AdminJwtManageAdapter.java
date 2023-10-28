package backend.wal.admin.adapter;

import backend.wal.admin.application.port.out.AdminTokenResponseDto;
import backend.wal.admin.application.port.out.AdminJwtManagePort;
import backend.wal.auth.adapter.jwt.JwtManagerAdapter;
import org.springframework.stereotype.Component;

@Component
public class AdminJwtManageAdapter implements AdminJwtManagePort {

    private final JwtManagerAdapter jwtManagerAdapter;

    public AdminJwtManageAdapter(final JwtManagerAdapter jwtManagerAdapter) {
        this.jwtManagerAdapter = jwtManagerAdapter;
    }

    @Override
    public AdminTokenResponseDto createToken(Long adminId, String role) {
        return new AdminTokenResponseDto(
                jwtManagerAdapter.createAccessToken(adminId, role),
                jwtManagerAdapter.getAccessTokenExpiredIn()
        );
    }
}
