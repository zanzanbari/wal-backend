package backend.wal.user.application.service;

import backend.wal.user.application.port.in.ChangeUserInfoUseCase;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

@AppService
@Transactional
public class ChangeUserInfoService implements ChangeUserInfoUseCase {

    private final UserRepository userRepository;

    public ChangeUserInfoService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String changeNickname(String newNickname, Long userId) {
        User user = UserServiceUtils.findExistsUserByUserId(userRepository, userId);
        user.changeNickname(newNickname);
        return user.getNickname();
    }
}
