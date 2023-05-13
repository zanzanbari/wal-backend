package backend.wal.notification.application.service;

import backend.wal.notification.application.port.in.FcmTokenUseCase;
import backend.wal.notification.application.port.in.InitFcmRequestDto;
import backend.wal.notification.application.port.in.UpdateFcmTokenRequestDto;
import backend.wal.notification.domain.FcmToken;
import backend.wal.notification.domain.repository.FcmTokenRepository;
import backend.wal.notification.exception.NotFoundFcmTokenException;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class FcmTokenService implements FcmTokenUseCase {

    private final FcmTokenRepository fcmTokenRepository;

    public FcmTokenService(final FcmTokenRepository fcmTokenRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
    }

    @Override
    @Transactional
    public void register(InitFcmRequestDto requestDto) {
        if (!fcmTokenRepository.existsFcmTokenByUserId(requestDto.getUserId())) {
            fcmTokenRepository.save(FcmToken.newInstance(requestDto.getUserId(), requestDto.getFcmToken()));
        }
    }

    @Override
    @Transactional
    public void update(UpdateFcmTokenRequestDto requestDto) {
        FcmToken fcmToken = fcmTokenRepository.findFcmTokenByUserId(requestDto.getUserId())
                .orElseThrow(() -> NotFoundFcmTokenException.notExists(requestDto.getUserId()));
        if (fcmToken.isNotSameWith(requestDto.getFcmToken())) {
            fcmToken.updateTokenValue(requestDto.getFcmToken());
        }
    }
}
