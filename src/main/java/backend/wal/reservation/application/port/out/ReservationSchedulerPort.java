package backend.wal.reservation.application.port.out;

public interface ReservationSchedulerPort {

    void sendMessageAfterDelay(Runnable task, long delayTime, Long reservationId);

    void cancelMessage(Long reservationId);
}
