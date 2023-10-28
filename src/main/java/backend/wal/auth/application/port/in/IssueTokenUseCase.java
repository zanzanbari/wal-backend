package backend.wal.auth.application.port.in;

public interface IssueTokenUseCase {

    TokenResponseDto issueForNewUser(Long userId, String role);

    TokenResponseDto issueForAlreadyUser(Long userId, String role);

    String reissue(String validRefreshToken, String role);
}
