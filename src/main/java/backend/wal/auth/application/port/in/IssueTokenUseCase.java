package backend.wal.auth.application.port.in;

public interface IssueTokenUseCase {

    TokenResponseDto issue(Long userId);

    String reissue(String validRefreshToken);
}
