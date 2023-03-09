package backend.wal.auth.application.dto.request;

import lombok.Getter;

@Getter
public final class InitFcmRequestDto {

    private final Long userId;
    private final String fcmToken;

    public InitFcmRequestDto(final Long userId, final String fcmToken) {
        this.userId = userId;
        this.fcmToken = fcmToken;
    }
}
