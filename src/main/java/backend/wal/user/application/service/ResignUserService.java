package backend.wal.user.application.service;

import backend.wal.user.application.port.in.ResignUserUseCase;
import backend.wal.user.application.port.out.DeleteResignUserInfoPort;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class ResignUserService implements ResignUserUseCase {

    private final UserRepository userRepository;
    private final DeleteResignUserInfoPort deleteResignUserInfoPort;

    public ResignUserService(final UserRepository userRepository,
                             final DeleteResignUserInfoPort deleteResignUserInfoPort) {
        this.userRepository = userRepository;
        this.deleteResignUserInfoPort = deleteResignUserInfoPort;
    }

    @Transactional
    public void resign(Long userId) {
        User user = UserServiceUtils.findExistsUserByUserId(userRepository, userId);
        deleteResignUserInfoPort.deleteAll(userId);
        user.resign();
    }
}
