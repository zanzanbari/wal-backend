package backend.wal.notification.application.port;

import backend.wal.onboard.domain.common.WalTimeType;

public interface NotificationSchedulerPort {

    void executePushNotification(WalTimeType timeType);
}
