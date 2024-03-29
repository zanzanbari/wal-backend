package backend.wal.reservation.web.dto;

import backend.wal.reservation.domain.aggregate.ShowStatus;
import backend.wal.reservation.application.port.in.dto.AddReservationRequestDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddReservationRequest {

    @NotBlank(message = "예약 메세지를 입력하세요")
    private String message;

    @NotBlank(message = "예약 날짜를 입력하세요")
    private String localDate;

    @NotBlank(message = "예약 시간을 입력하세요")
    private String localTime;

    @NotNull(message = "보여주기 여부를 입력하세요")
    private ShowStatus showStatus;

    public AddReservationRequestDto toServiceDto(Long userId) {
        return AddReservationRequestDto.of(userId, message, localDate, localTime, showStatus);
    }
}
