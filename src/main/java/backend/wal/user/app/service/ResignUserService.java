package backend.wal.user.app.service;

import backend.wal.user.domain.ResignUserScheduler;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResignUserService {

    private static final long MILLIS_OF_ONE_DAY = 1000 * 60 * 60 * 24;

    private final UserRepository userRepository;
    private final ResignUserScheduler resignUserScheduler;

    @Transactional
    public void resign(Long userId) {
        User user = UserServiceUtils.findUserBy(userRepository, userId);
        user.resign();

        Runnable userDeleteTask = () -> userRepository.delete(user);
        resignUserScheduler.resignAfterDay(MILLIS_OF_ONE_DAY, userDeleteTask);
    }
}
