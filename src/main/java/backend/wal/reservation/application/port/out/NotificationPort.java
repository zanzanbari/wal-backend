package backend.wal.reservation.application.port.out;

public interface NotificationPort {

    void sendCall(NotificationRequestDto requestDto);
}
