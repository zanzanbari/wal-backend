package backend.wal.scheduler;

import backend.wal.notification.application.port.in.NotificationUseCase;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.onboarding.domain.repository.OnboardingTimeRepository;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.wal.wal.common.domain.WalTimeType.*;

@Component
public class WalNotificationScheduler {

    private final NotificationUseCase notificationUseCase;
    private final OnboardingTimeRepository onboardingTimeRepository;
    private final TodayWalRepository todayWalRepository;

    public WalNotificationScheduler(final NotificationUseCase notificationUseCase,
                                    final OnboardingTimeRepository onboardingTimeRepository,
                                    final TodayWalRepository todayWalRepository) {
        this.notificationUseCase = notificationUseCase;
        this.onboardingTimeRepository = onboardingTimeRepository;
        this.todayWalRepository = todayWalRepository;
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

    @Transactional
    public void executePushNotification(WalTimeType timeType) {
        List<Long> userIds = onboardingTimeRepository.findUserIdsByTimeType(timeType);
        List<TodayWal> todayWals = todayWalRepository.findTodayWalByUserIdInAndTimeType(userIds, timeType);
        todayWals.forEach(todayWal -> notificationUseCase.sendMessage(todayWal.getUserId(), todayWal.getMessage()));
    }
}
