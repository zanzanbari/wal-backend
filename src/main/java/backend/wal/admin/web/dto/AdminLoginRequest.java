package backend.wal.admin.web.dto;

import backend.wal.admin.application.port.in.AdminLoginRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AdminLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,40}")
    private String password;

    public AdminLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AdminLoginRequestDto toServiceDto() {
        return new AdminLoginRequestDto(email, password);
    }
}
