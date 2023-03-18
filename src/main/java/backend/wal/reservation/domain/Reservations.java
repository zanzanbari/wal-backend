package backend.wal.reservation.domain;

import backend.wal.reservation.app.dto.ReservationCalendarResponseDto;
import backend.wal.reservation.app.dto.ReservationHistoryResponseDto;
import backend.wal.reservation.domain.aggregate.entity.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public final class Reservations {

    private final List<Reservation> values;

    public Reservations(final List<Reservation> values) {
        this.values = values;
    }

    public List<ReservationHistoryResponseDto> extractNotDoneReservation() {
        return values.stream()
                .filter(Reservation::isNotDone)
                .map(Reservation::getDetailSendDateInfo)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<ReservationHistoryResponseDto> extractDoneReservation() {
        return values.stream()
                .filter(Reservation::isDone)
                .map(Reservation::getDetailSendDateInfo)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<ReservationCalendarResponseDto> extractDateForCalender() {
        return values.stream()
                .map(Reservation::getSendDueDate)
                .map(ReservationCalendarResponseDto::new)
                .collect(Collectors.toUnmodifiableList());
    }
}
