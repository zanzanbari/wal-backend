package backend.wal.auth.app.service;

import backend.wal.auth.app.dto.request.LoginRequestDto;

public interface AuthService {
    Long login(LoginRequestDto requestDto);
}
