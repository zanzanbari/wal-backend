package backend.wal.notification.application.port.in;

public interface RegisterFcmTokenUseCase {

    void register(InitFcmRequestDto requestDto);
}
