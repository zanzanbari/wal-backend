package backend.wal.reservation.application.port.out;

@Deprecated
public interface ReservationSchedulerPort {

    void sendMessageAfterDelay(Runnable task, long delayTime, Long reservationId);

    void cancelMessage(Long reservationId);

    void shoutDown();
}
