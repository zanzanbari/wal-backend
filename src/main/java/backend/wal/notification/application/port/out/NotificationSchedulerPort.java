package backend.wal.notification.application.port.out;

import backend.wal.wal.common.domain.WalTimeType;

public interface NotificationSchedulerPort {

    void executePushNotification(WalTimeType timeType);
}
