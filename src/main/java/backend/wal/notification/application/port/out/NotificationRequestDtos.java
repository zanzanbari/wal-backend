package backend.wal.notification.application.port.out;

import java.util.List;
import java.util.stream.Collectors;

public final class NotificationRequestDtos {

    private final List<NotificationRequestDto> values;

    public NotificationRequestDtos(final List<NotificationRequestDto> values) {
        this.values = values;
    }

    public List<String> getFcmTokens() {
        return values.stream()
                .map(NotificationRequestDto::getFcmToken)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<NotificationRequestDto> getValues() {
        return values;
    }
}
