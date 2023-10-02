package backend.wal.admin.application.service;

import backend.wal.admin.application.port.in.AdminLoginRequestDto;
import backend.wal.admin.application.port.out.PasswordManagePort;
import backend.wal.admin.domain.aggregate.Admin;
import backend.wal.admin.domain.repository.AdminRepository;
import backend.wal.admin.exception.NotFoundAdminException;
import backend.wal.admin.exception.UnAuthorizedPasswordException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminAuthServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordManagePort passwordManagePort;

    @InjectMocks
    private AdminAuthService adminAuthService;

    private final AdminLoginRequestDto requestDto =
            new AdminLoginRequestDto("test@example.com", "testPassword");

    @DisplayName("이메일과 비밀번호를 받아 새로운 관리자를 생성한다")
    @Test
    void create() {
        // given
        when(passwordManagePort.encode(requestDto.getPassword()))
                .thenReturn("encodedPassword");

        // when
        adminAuthService.create(requestDto);

        // then
        verify(adminRepository, atMostOnce()).save(any(Admin.class));
    }

    @DisplayName("이메일과 비밀번호를 받아 존재하는 관리자 정보(관리자 id) 를 반환한다")
    @Test
    void login() {
        // given
        Admin existingAdmin = new Admin("test@example.com", "encodedPassword");
        when(adminRepository.findByEmail(requestDto.getEmail()))
                .thenReturn(Optional.of(existingAdmin));
        when(passwordManagePort.isMatch(requestDto.getPassword(), existingAdmin.getPassword()))
                .thenReturn(true);

        // when
        Long adminId = adminAuthService.login(requestDto);

        // then
        verify(adminRepository).findByEmail(requestDto.getEmail());
        verify(passwordManagePort).isMatch(requestDto.getPassword(), existingAdmin.getPassword());
        assertEquals(existingAdmin.getId(), adminId);
    }

    @DisplayName("이메일에 대한 관리자가 존재하지 않으면 예외가 발생한다")
    @Test
    void fail_login_notFound_admin() {
        // given
        when(adminRepository.findByEmail(requestDto.getEmail()))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> adminAuthService.login(requestDto))
                .isInstanceOf(NotFoundAdminException.class)
                .hasMessage(String.format("존재하지 않는 관리자 계정 (%s) 입니다", requestDto.getEmail()));
    }

    @DisplayName("비밀번호가 틀리면 예외가 발생한다")
    @Test
    void fail_login_wrong_password() {
        // given
        AdminLoginRequestDto requestDto = new AdminLoginRequestDto("test@example.com", "wrongPassword");
        Admin existingAdmin = new Admin("test@example.com", "encodedPassword");
        when(adminRepository.findByEmail(requestDto.getEmail()))
                .thenReturn(Optional.of(existingAdmin));
        when(passwordManagePort.isMatch(requestDto.getPassword(), existingAdmin.getPassword()))
                .thenReturn(false);

        // when, then
        assertThatThrownBy(() -> adminAuthService.login(requestDto))
                .isInstanceOf(UnAuthorizedPasswordException.class)
                .hasMessage("비밀번호가 일치하지 않습니다");
    }
}