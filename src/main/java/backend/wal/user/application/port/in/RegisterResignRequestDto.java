package backend.wal.user.application.port.in;

import backend.wal.user.domain.aggregate.ResignReason;

import java.util.Collections;
import java.util.Set;

public final class RegisterResignRequestDto {

    private final Long userId;
    private final Set<ResignReason> reasons;

    public RegisterResignRequestDto(final Long userId, final Set<ResignReason> reasons) {
        this.userId = userId;
        this.reasons = reasons;
    }

    public Long getUserId() {
        return userId;
    }

    public Set<ResignReason> getReasons() {
        return Collections.unmodifiableSet(reasons);
    }
}
