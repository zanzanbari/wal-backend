package backend.wal.auth.adapter;

import backend.wal.auth.application.port.out.FcmTokenPort;
import backend.wal.notification.application.port.in.FcmTokenUseCase;
import backend.wal.notification.application.port.in.InitFcmRequestDto;
import backend.wal.notification.application.port.in.UpdateFcmTokenRequestDto;

import org.springframework.stereotype.Component;

@Component
public final class FcmTokenAdapter implements FcmTokenPort {

    private final FcmTokenUseCase fcmTokenUseCase;

    public FcmTokenAdapter(final FcmTokenUseCase fcmTokenUseCase) {
        this.fcmTokenUseCase = fcmTokenUseCase;
    }

    @Override
    public void registerCall(InitFcmRequestDto requestDto) {
        fcmTokenUseCase.register(requestDto);
    }

    @Override
    public void checkAndUpdateCall(UpdateFcmTokenRequestDto requestDto) {
        fcmTokenUseCase.update(requestDto);
    }
}
