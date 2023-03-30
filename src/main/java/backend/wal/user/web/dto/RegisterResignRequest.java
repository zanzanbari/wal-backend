package backend.wal.user.web.dto;

import backend.wal.user.application.port.in.RegisterResignRequestDto;
import backend.wal.user.domain.aggregate.ResignReason;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterResignRequest {

    @NotNull(message = "탈퇴 이유를 선택하세요")
    private Set<ResignReason> reasons;

    public RegisterResignRequest(final Set<ResignReason> reasons) {
        this.reasons = reasons;
    }

    public RegisterResignRequestDto toServiceDto(Long userId) {
        return new RegisterResignRequestDto(userId, reasons);
    }
}
