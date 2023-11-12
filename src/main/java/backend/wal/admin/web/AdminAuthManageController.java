package backend.wal.admin.web;

import backend.wal.admin.application.port.in.AdminAuthUseCase;
import backend.wal.admin.application.port.in.AdminLoginResponseDto;
import backend.wal.admin.application.port.out.AdminJwtManagePort;
import backend.wal.admin.application.port.out.AdminTokenResponseDto;
import backend.wal.admin.web.dto.AdminLoginRequest;
import backend.wal.admin.web.dto.AdminLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static backend.wal.support.utils.HttpHeaderUtils.AUTHORIZATION;
import static backend.wal.support.utils.HttpHeaderUtils.withBearerToken;

@RestController
@RequestMapping("/admin/auth")
public class AdminAuthManageController {

    private final AdminAuthUseCase adminAuthUseCase;
    private final AdminJwtManagePort adminJwtManagePort;

    public AdminAuthManageController(final AdminAuthUseCase adminAuthUseCase,
                                     final AdminJwtManagePort adminJwtManagePort) {
        this.adminAuthUseCase = adminAuthUseCase;
        this.adminJwtManagePort = adminJwtManagePort;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody AdminLoginRequest request) {
        adminAuthUseCase.create(request.toServiceDto());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@Valid @RequestBody AdminLoginRequest request) {
        AdminLoginResponseDto loginResponse = adminAuthUseCase.login(request.toServiceDto());
        AdminTokenResponseDto responseDto = adminJwtManagePort.createToken(
                loginResponse.getUserId(),
                loginResponse.getRole()
        );
        return ResponseEntity.ok()
                .header(AUTHORIZATION, withBearerToken(responseDto.getToken()))
                .body(new AdminLoginResponse(responseDto.getExpiredIn()));
    }
}
