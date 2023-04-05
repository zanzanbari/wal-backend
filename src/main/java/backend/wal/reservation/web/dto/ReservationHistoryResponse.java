package backend.wal.reservation.web.dto;

import backend.wal.reservation.application.port.in.dto.ReservationHistoryResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationHistoryResponse {

    private List<ReservationHistoryResponseDto> toDoneData;
    private List<ReservationHistoryResponseDto> doneData;

    public ReservationHistoryResponse(final List<ReservationHistoryResponseDto> toDoneData,
                                      final List<ReservationHistoryResponseDto> doneData) {
        this.toDoneData = toDoneData;
        this.doneData = doneData;
    }
}
