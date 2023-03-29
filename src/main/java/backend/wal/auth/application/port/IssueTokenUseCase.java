package backend.wal.auth.application.port;

import backend.wal.auth.application.port.dto.TokenResponse;

public interface IssueTokenUseCase {

    TokenResponse issue(Long userId);

    String reissue(String validRefreshToken);
}
