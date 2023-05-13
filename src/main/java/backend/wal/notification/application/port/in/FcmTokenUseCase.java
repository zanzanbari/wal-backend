package backend.wal.notification.application.port.in;

public interface FcmTokenUseCase {

    void register(InitFcmRequestDto requestDto);

    void update(UpdateFcmTokenRequestDto requestDto);
}
