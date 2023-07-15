package backend.wal.notification.application.port.out;

public final class NotificationRequestDto {

    private final String fcmToken;
    private final String contents;

    public NotificationRequestDto(final String fcmToken, final String contents) {
        this.fcmToken = fcmToken;
        this.contents = contents;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public String getContents() {
        return contents;
    }
}
