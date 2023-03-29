package backend.wal.notification.application.port;

import backend.wal.wal.common.domain.WalTimeType;

public interface NotificationSchedulerPort {

    void executePushNotification(WalTimeType timeType);
}
