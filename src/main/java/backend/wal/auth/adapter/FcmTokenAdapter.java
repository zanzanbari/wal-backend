package backend.wal.auth.adapter;

import backend.wal.auth.application.port.out.RegisterFcmPort;
import backend.wal.notification.application.port.in.InitFcmRequestDto;
import backend.wal.notification.application.port.in.RegisterFcmTokenUseCase;

import org.springframework.stereotype.Component;

@Component
public final class RegisterFcmAdapter implements RegisterFcmPort {

    private final RegisterFcmTokenUseCase registerFcmTokenUseCase;

    public RegisterFcmAdapter(final RegisterFcmTokenUseCase registerFcmTokenUseCase) {
        this.registerFcmTokenUseCase = registerFcmTokenUseCase;
    }

    @Override
    public void registerCall(InitFcmRequestDto requestDto) {
        registerFcmTokenUseCase.register(requestDto);
    }
}
