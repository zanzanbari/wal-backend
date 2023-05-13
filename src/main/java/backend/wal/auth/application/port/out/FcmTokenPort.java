package backend.wal.auth.application.port.out;

import backend.wal.notification.application.port.in.InitFcmRequestDto;
import backend.wal.notification.application.port.in.UpdateFcmTokenRequestDto;

public interface FcmTokenPort {

    void registerCall(InitFcmRequestDto requestDto);

    void checkAndUpdateCall(UpdateFcmTokenRequestDto requestDto);
}
