package backend.wal.reservation.application.port;

public interface ReservationSchedulerPort {

    void sendMessageAfterDelay(Runnable task, long delayTime, Long reservationId);

    void cancelMessage(Long reservationId);

    void shoutDown();
}
