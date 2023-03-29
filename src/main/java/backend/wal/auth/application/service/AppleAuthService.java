package backend.wal.auth.application.service;

import backend.wal.auth.application.port.AuthUseCase;
import backend.wal.auth.application.port.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleAuthService implements AuthUseCase {
    @Override
    public Long login(LoginRequestDto requestDto) {
        return null;
    }
}
