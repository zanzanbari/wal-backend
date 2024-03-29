package backend.wal.notification.application.service;

import backend.wal.notification.application.port.in.NotificationUseCase;
import backend.wal.notification.application.port.out.FirebaseMessagingPort;
import backend.wal.notification.domain.repository.FcmTokenRepository;
import backend.wal.notification.domain.FcmToken;
import backend.wal.notification.exception.NotFoundFcmTokenException;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class NotificationService implements NotificationUseCase {

    private final FirebaseMessagingPort firebaseMessagingPort;
    private final FcmTokenRepository fcmTokenRepository;

    public NotificationService(final FirebaseMessagingPort firebaseMessagingPort,
                               final FcmTokenRepository fcmTokenRepository) {
        this.firebaseMessagingPort = firebaseMessagingPort;
        this.fcmTokenRepository = fcmTokenRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void sendReservation(Long reservationId, Long userId, String content) {
        FcmToken fcmToken = fcmTokenRepository.findFcmTokenByUserId(userId)
                .orElseThrow(() -> NotFoundFcmTokenException.notExists(userId));
        firebaseMessagingPort.sendReservation(fcmToken.getValue(), reservationId, content);
    }
}
