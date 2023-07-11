package backend.wal.notification.application.port.in;

import java.util.List;

public final class NotificationTimeRequestDto {

    private final List<Long> userIds;

    public NotificationTimeRequestDto(final List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<Long> getUserIds() {
        return userIds;
    }
}
