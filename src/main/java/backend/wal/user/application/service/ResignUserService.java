package backend.wal.user.application.service;

import backend.wal.user.application.port.in.ResignUserUseCase;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class ResignUserService implements ResignUserUseCase {

    private final UserRepository userRepository;

    public ResignUserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void resign(Long userId) {
        User user = UserServiceUtils.findExistsUserByUserId(userRepository, userId);
        user.resign();
        // FIXME: 2023/06/20 : user delete 로직 수정 필요 (모두 삭제 or soft delete)
        userRepository.delete(user);
    }
}
