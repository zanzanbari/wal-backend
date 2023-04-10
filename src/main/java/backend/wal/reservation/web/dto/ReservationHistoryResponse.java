package backend.wal.reservation.web.dto;

import backend.wal.reservation.application.port.in.dto.ReservationHistoryResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationHistoryResponse {

    private List<ReservationHistoryResponseDto> notDoneData;
    private List<ReservationHistoryResponseDto> doneData;

    public ReservationHistoryResponse(final List<ReservationHistoryResponseDto> notDoneData,
                                      final List<ReservationHistoryResponseDto> doneData) {
        this.notDoneData = notDoneData;
        this.doneData = doneData;
    }

    public List<ReservationHistoryResponseDto> getNotDoneData() {
        return notDoneData;
    }

    public List<ReservationHistoryResponseDto> getDoneData() {
        return doneData;
    }
}
