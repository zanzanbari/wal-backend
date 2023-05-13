package backend.wal.auth.application.port.out;

import backend.wal.notification.application.port.in.InitFcmRequestDto;

public interface RegisterFcmPort {

    void registerCall(InitFcmRequestDto requestDto);
}
