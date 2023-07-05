package backend.wal.scheduler;

import backend.wal.notification.application.port.in.NotificationTimeRequestDto;
import backend.wal.notification.application.port.in.NotificationUseCase;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.onboarding.domain.repository.OnboardingTimeRepository;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static backend.wal.wal.common.domain.WalTimeType.*;

@Component
public class WalNotificationScheduler {

    private final NotificationUseCase notificationUseCase;
    private final OnboardingTimeRepository onboardingTimeRepository;

    public WalNotificationScheduler(final NotificationUseCase notificationUseCase,
                                    final OnboardingTimeRepository onboardingTimeRepository) {
        this.notificationUseCase = notificationUseCase;
        this.onboardingTimeRepository = onboardingTimeRepository;
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
        List<Long> userIds = onboardingTimeRepository.findUserIdsByTimeType(timeType);
        notificationUseCase.sendMessage(new NotificationTimeRequestDto(userIds));
    }
}
