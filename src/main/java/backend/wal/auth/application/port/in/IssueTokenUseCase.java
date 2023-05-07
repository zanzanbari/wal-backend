package backend.wal.auth.application.port.in;

public interface IssueTokenUseCase {

    TokenResponseDto issueForNewUser(Long userId);

    TokenResponseDto issueForAlreadyUser(Long userId);

    String reissue(String validRefreshToken);
}
