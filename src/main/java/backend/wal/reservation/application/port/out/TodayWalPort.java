package backend.wal.reservation.application.port.out;

public interface TodayWalPort {

    void registerReservationCall(Long userId, String message);

    void deleteReservationCall(Long userId);
}
