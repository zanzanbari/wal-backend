package backend.wal.reservation.adapter;

import backend.wal.reservation.application.port.out.NotificationRequestDto;
import backend.wal.reservation.application.port.out.NotificationPort;
import backend.wal.notification.application.port.in.NotificationUseCase;

import org.springframework.stereotype.Component;

@Component
public final class NotificationAdapter implements NotificationPort {

    private final NotificationUseCase notificationUseCase;

    public NotificationAdapter(final NotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }

    @Override
    public void sendCall(NotificationRequestDto requestDto) {
        notificationUseCase.sendMessage(requestDto.getReservationId(), requestDto.getUserId(), requestDto.getMessage());
    }
}
