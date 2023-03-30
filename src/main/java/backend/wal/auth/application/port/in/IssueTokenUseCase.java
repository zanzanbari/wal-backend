package backend.wal.auth.application.port.in;

import backend.wal.auth.application.port.out.TokenResponseDto;

public interface IssueTokenUseCase {

    TokenResponseDto issue(Long userId);

    String reissue(String validRefreshToken);
}
