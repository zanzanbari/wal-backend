package backend.wal.reservation.adapter;

import backend.wal.notification.application.port.NotificationUseCase;
import backend.wal.reservation.application.port.out.NotificationPort;
import org.springframework.stereotype.Component;

@Component
public final class NotificationAdapter implements NotificationPort {

    private final NotificationUseCase notificationUseCase;

    public NotificationAdapter(final NotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }

    @Override
    public void sendCall(Long userId, String message) {
        notificationUseCase.sendMessage(userId, message);
    }
}
