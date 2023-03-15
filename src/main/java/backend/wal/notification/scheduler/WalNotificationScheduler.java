package backend.wal.notification.scheduler;

import backend.wal.notification.service.NotificationService;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.onboarding.domain.repository.OnboardingTimeRepository;
import backend.wal.wal.domain.entity.TodayWal;
import backend.wal.wal.domain.repository.TodayWalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.wal.onboarding.domain.entity.WalTimeType.*;

@Component
@RequiredArgsConstructor
public class WalNotificationScheduler implements NotificationScheduler {

    private final NotificationService notificationService;
    private final OnboardingTimeRepository onboardingTimeRepository;
    private final TodayWalRepository todayWalRepository;

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

    @Override
    @Transactional
    public void executePushNotification(WalTimeType timeType) {
        List<Long> userIds = onboardingTimeRepository.findUserIdsByTimeType(timeType);
        List<TodayWal> todayWals = todayWalRepository.findTodayWalByUserIdInAndTimeType(userIds, timeType);
        todayWals.forEach(todayWal -> notificationService
                .sendMessage(todayWal.getUserId(), todayWal.getMessage()));
    }
}
