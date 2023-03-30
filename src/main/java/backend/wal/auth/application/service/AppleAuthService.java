package backend.wal.auth.application.service;

import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.port.in.LoginRequestDto;
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
