package backend.wal.admin.application.service;

import backend.wal.admin.application.port.in.AdminAuthUseCase;
import backend.wal.admin.application.port.in.AdminLoginRequestDto;
import backend.wal.admin.application.port.in.AdminLoginResponseDto;
import backend.wal.admin.application.port.out.PasswordManagePort;
import backend.wal.admin.domain.aggregate.Admin;
import backend.wal.admin.domain.repository.AdminRepository;
import backend.wal.admin.exception.NotFoundAdminException;
import backend.wal.admin.exception.UnAuthorizedPasswordException;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class AdminAuthService implements AdminAuthUseCase {

    private final AdminRepository adminRepository;
    private final PasswordManagePort passwordManagePort;

    public AdminAuthService(final AdminRepository adminRepository, final PasswordManagePort passwordManagePort) {
        this.adminRepository = adminRepository;
        this.passwordManagePort = passwordManagePort;
    }

    @Override
    @Transactional
    public void create(AdminLoginRequestDto requestDto) {
        adminRepository.save(new Admin(
                requestDto.getEmail(),
                passwordManagePort.encode(requestDto.getPassword())
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminLoginResponseDto login(AdminLoginRequestDto requestDto) {
        Admin admin = findExistAdminByEmail(requestDto.getEmail());
        validateMatchPassword(requestDto.getPassword(), admin.getPassword());
        return new AdminLoginResponseDto(admin.getId(), admin.getRole());
    }

    private Admin findExistAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> NotFoundAdminException.notExists(email));
    }

    private void validateMatchPassword(String requestPassword, String adminPassword) {
        if (!passwordManagePort.isMatch(requestPassword, adminPassword)) {
            throw UnAuthorizedPasswordException.wrong();
        }
    }
}
