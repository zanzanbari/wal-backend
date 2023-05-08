package backend.wal.reservation.domain;

import backend.wal.reservation.application.port.in.dto.ReservationCalenderResponseDto;
import backend.wal.reservation.application.port.in.dto.ReservationHistoryResponseDto;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.view.ReservationHistory;

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
                .map(Reservation::toHistory)
                .map(ReservationHistory::getDetailSendDateInfo)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<ReservationHistoryResponseDto> extractDoneReservation() {
        return values.stream()
                .filter(Reservation::isDone)
                .map(Reservation::toHistory)
                .map(ReservationHistory::getDetailSendDateInfo)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<ReservationCalenderResponseDto> extractDateForCalender() {
        return values.stream()
                .map(Reservation::getSendDueDate)
                .map(ReservationCalenderResponseDto::create)
                .collect(Collectors.toUnmodifiableList());
    }
}
