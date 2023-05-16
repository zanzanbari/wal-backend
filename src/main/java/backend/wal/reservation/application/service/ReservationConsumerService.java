package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.in.ReservationConsumerUseCase;
import backend.wal.reservation.application.port.in.dto.DelayReservationMessageRequestDto;
import backend.wal.reservation.application.port.out.NotificationPort;
import backend.wal.support.annotation.AppService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AppService
public class ReservationConsumerService implements ReservationConsumerUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationConsumerService.class);

    private final NotificationPort notificationPort;

    public ReservationConsumerService(final NotificationPort notificationPort) {
        this.notificationPort = notificationPort;
    }

    @Override
    public void consumeMessageFromQueue(DelayReservationMessageRequestDto requestDto) {
        LOGGER.info("예약 메시지(등록 시간 : {})를 reservation.queue 로부터 가져왔습니다", requestDto.getSendDueDate());
        notificationPort.sendCall(requestDto.toNotificationRequest());
    }
}
