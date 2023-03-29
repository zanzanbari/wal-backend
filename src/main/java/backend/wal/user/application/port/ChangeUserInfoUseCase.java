package backend.wal.user.application.port;

public interface ChangeUserInfoUseCase {

    String changeNickname(String newNickname, Long userId);
}
