package backend.wal.reservation.application.port.out;

public interface TodayWalPort {

    void registerReservationCall(ReservationTodayWalRequestDto requestDto);

    void deleteReservationCall(Long userId);
}
