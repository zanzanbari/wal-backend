package backend.wal.user.application.port.in;

public interface ChangeUserInfoUseCase {

    String changeNickname(String newNickname, Long userId);
}
