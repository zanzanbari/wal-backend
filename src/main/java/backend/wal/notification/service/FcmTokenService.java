package backend.wal.notification.service;

import backend.wal.auth.app.dto.request.InitFcmRequestDto;
import backend.wal.notification.domain.entity.FcmToken;
import backend.wal.notification.domain.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public void register(InitFcmRequestDto requestDto) {
        if (!fcmTokenRepository.existsFcmTokenByUserId(requestDto.getUserId())) {
            fcmTokenRepository.save(FcmToken.newInstance(requestDto.getUserId(), requestDto.getFcmToken()));
        }
    }
}
