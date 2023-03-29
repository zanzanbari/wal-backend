package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.dto.ReservationNotificationRequestDto;
import backend.wal.reservation.application.port.ReservationSchedulerPort;
import backend.wal.notification.application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationNotificationService {

    private final ReservationSchedulerPort reservationSchedulerPort;
    private final NotificationService notificationService;

    public void send(ReservationNotificationRequestDto requestDto) {
        Runnable notificationTask = () -> {
            notificationService.sendMessage(requestDto.getUserId(), requestDto.getMessage());
            reservationSchedulerPort.shoutDown();
        };
        reservationSchedulerPort.sendMessageAfterDelay(notificationTask,
                requestDto.getDelayTime(),
                requestDto.getReservationId()
        );
    }

    public void cancel(Long reservationId) {
        reservationSchedulerPort.cancelMessage(reservationId);
    }
}
