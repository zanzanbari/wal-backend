package backend.wal.notification.application.port.in;

public final class InitFcmRequestDto {

    private final Long userId;
    private final String fcmToken;

    public InitFcmRequestDto(final Long userId, final String fcmToken) {
        this.userId = userId;
        this.fcmToken = fcmToken;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
