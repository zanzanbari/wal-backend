package backend.wal.reservation.application.port.out;

public interface ReservationProducerPort {

    void sendMessageToQueue(PublishMessageRequestDto requestDto);
}
