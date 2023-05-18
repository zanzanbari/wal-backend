package backend.wal.user.application.service;

import backend.wal.user.application.port.out.ResignUserSchedulerPort;
import backend.wal.user.application.port.in.ResignUserUseCase;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class ResignUserService implements ResignUserUseCase {

    private static final long MILLIS_OF_ONE_DAY = 1000 * 60 * 60 * 24;

    private final UserRepository userRepository;
    private final ResignUserSchedulerPort resignUserSchedulerPort;

    public ResignUserService(final UserRepository userRepository, final ResignUserSchedulerPort resignUserSchedulerPort) {
        this.userRepository = userRepository;
        this.resignUserSchedulerPort = resignUserSchedulerPort;
    }

    @Transactional
    public void resign(Long userId) {
        User user = UserServiceUtils.findExistsUserByUserId(userRepository, userId);
        user.resign();

        resignUserSchedulerPort.resignAfterDay(() -> userRepository.delete(user), MILLIS_OF_ONE_DAY);
    }
}
