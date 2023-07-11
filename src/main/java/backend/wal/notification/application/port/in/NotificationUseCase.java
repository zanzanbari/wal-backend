package backend.wal.notification.application.port.in;

public interface NotificationUseCase {

    void sendMessage(NotificationTimeRequestDto requestDto);

    void sendReservation(Long reservationId, Long userId);
}
