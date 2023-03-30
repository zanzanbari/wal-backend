package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.in.dto.ReservationNotificationRequestDto;
import backend.wal.reservation.application.port.in.ReservationNotificationUseCase;
import backend.wal.reservation.application.port.out.ReservationSchedulerPort;
import backend.wal.reservation.application.port.out.NotificationPort;
import backend.wal.support.annotation.AppService;

@AppService
public class ReservationNotificationService implements ReservationNotificationUseCase {

    private final ReservationSchedulerPort reservationSchedulerPort;
    private final NotificationPort notificationPort;

    public ReservationNotificationService(final ReservationSchedulerPort reservationSchedulerPort,
                                          final NotificationPort notificationPort) {
        this.reservationSchedulerPort = reservationSchedulerPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public void send(ReservationNotificationRequestDto requestDto) {
        Runnable notificationTask = () -> {
            notificationPort.sendCall(requestDto.getUserId(), requestDto.getMessage());
            reservationSchedulerPort.shoutDown();
        };
        reservationSchedulerPort.sendMessageAfterDelay(notificationTask,
                requestDto.getDelayTime(),
                requestDto.getReservationId()
        );
    }

    @Override
    public void cancel(Long reservationId) {
        reservationSchedulerPort.cancelMessage(reservationId);
    }
}
