package backend.wal.notification.scheduler;

import backend.wal.onboarding.domain.entity.WalTimeType;

public interface NotificationScheduler {
    void executePushNotification(WalTimeType timeType);
}
