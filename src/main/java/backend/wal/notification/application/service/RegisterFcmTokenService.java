package backend.wal.notification.application.service;

import backend.wal.auth.application.port.dto.InitFcmRequestDto;
import backend.wal.notification.application.port.RegisterFcmTokenUseCase;
import backend.wal.notification.domain.FcmToken;
import backend.wal.notification.domain.repository.FcmTokenRepository;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class RegisterFcmTokenService implements RegisterFcmTokenUseCase {

    private final FcmTokenRepository fcmTokenRepository;

    public RegisterFcmTokenService(final FcmTokenRepository fcmTokenRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
    }

    @Override
    @Transactional
    public void register(InitFcmRequestDto requestDto) {
        if (!fcmTokenRepository.existsFcmTokenByUserId(requestDto.getUserId())) {
            fcmTokenRepository.save(FcmToken.newInstance(requestDto.getUserId(), requestDto.getFcmToken()));
        }
    }
}
