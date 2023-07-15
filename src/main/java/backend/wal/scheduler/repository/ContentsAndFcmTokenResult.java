package backend.wal.scheduler.repository;

public class ContentsAndFcmTokenResult {

    private final String message;
    private final String fcmToken;

    public ContentsAndFcmTokenResult(final String message, final String fcmToken) {
        this.message = message;
        this.fcmToken = fcmToken;
    }

    public String getMessage() {
        return message;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
