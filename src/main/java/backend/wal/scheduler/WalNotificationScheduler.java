package backend.wal.scheduler;

import backend.wal.notification.application.port.out.FirebaseMessagingPort;
import backend.wal.notification.application.port.out.NotificationRequestDto;
import backend.wal.notification.application.port.out.NotificationRequestDtos;
import backend.wal.scheduler.repository.PushNotificationRepository;
import backend.wal.wal.common.domain.WalTimeType;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Collectors;

import static backend.wal.wal.common.domain.WalTimeType.*;

@Component
public class WalNotificationScheduler {

    private final FirebaseMessagingPort firebaseMessagingPort;
    private final PushNotificationRepository pushNotificationRepository;

    public WalNotificationScheduler(final FirebaseMessagingPort firebaseMessagingPort,
                                    final PushNotificationRepository pushNotificationRepository) {
        this.firebaseMessagingPort = firebaseMessagingPort;
        this.pushNotificationRepository = pushNotificationRepository;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void morningNotification() {
        executePushNotification(MORNING);
    }

    @Scheduled(cron = "0 0 14 * * *")
    public void afternoonNotification() {
        executePushNotification(AFTERNOON);
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void nightNotification() {
        executePushNotification(NIGHT);
    }

    public void executePushNotification(WalTimeType timeType) {
        List<NotificationRequestDto> requestDtos = pushNotificationRepository
                .findTodayWalMessageWithUserIdByTimeType(timeType)
                .stream()
                .map(todayWalContentsAndFcmTokenValue ->
                        new NotificationRequestDto(
                                todayWalContentsAndFcmTokenValue.getFcmToken(),
                                todayWalContentsAndFcmTokenValue.getMessage())
                )
                .collect(Collectors.toUnmodifiableList());
        firebaseMessagingPort.sendMessage(new NotificationRequestDtos(requestDtos));
    }
}
