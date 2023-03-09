package backend.wal.auth.application.service;

import backend.wal.auth.application.dto.request.LoginRequestDto;

public interface AuthService {
    Long login(LoginRequestDto requestDto);
}
