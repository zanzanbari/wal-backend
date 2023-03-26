package backend.wal.reservation.app.service;

import backend.wal.notification.service.NotificationService;
import backend.wal.reservation.app.dto.ReservationNotificationRequestDto;
import backend.wal.reservation.domain.ReservationScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationNotificationService {

    private final ReservationScheduler reservationScheduler;
    private final NotificationService notificationService;

    public void send(ReservationNotificationRequestDto requestDto) {
        Runnable notificationTask = () -> {
            notificationService.sendMessage(requestDto.getUserId(), requestDto.getMessage());
            reservationScheduler.shoutDown();
        };
        reservationScheduler.sendMessageAfterDelay(notificationTask,
                requestDto.getDelayTime(),
                requestDto.getReservationId()
        );
    }

    public void cancel(Long reservationId) {
        reservationScheduler.cancelMessage(reservationId);
    }
}
