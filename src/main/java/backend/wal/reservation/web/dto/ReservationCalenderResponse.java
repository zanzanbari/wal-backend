package backend.wal.reservation.web.dto;

import backend.wal.reservation.application.port.in.dto.ReservationCalenderResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationCalenderResponse {

    private List<LocalDate> reserveDates;

    private ReservationCalenderResponse() {
    }

    private ReservationCalenderResponse(final List<LocalDate> reserveDates) {
        this.reserveDates = reserveDates;
    }

    public static ReservationCalenderResponse create(final List<ReservationCalenderResponseDto> responseDtos) {
        List<LocalDate> reserveDates = responseDtos.stream()
                .map(ReservationCalenderResponseDto::getReserveDate)
                .collect(Collectors.toUnmodifiableList());
        return new ReservationCalenderResponse(reserveDates);
    }

    public List<LocalDate> getReserveDates() {
        return reserveDates;
    }
}
