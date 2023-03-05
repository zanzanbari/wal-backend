package backend.wal.auth.app.service;

import backend.wal.auth.app.dto.request.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleAuthService implements AuthService {
    @Override
    public Long login(LoginRequestDto requestDto) {
        return null;
    }
}
