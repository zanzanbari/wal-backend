package backend.wal.user.service;

import backend.wal.user.domain.entity.User;
import backend.wal.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;

public class UserServiceUtils {

    private UserServiceUtils() {
        throw new IllegalStateException("Utility class");
    }

    @NotNull
    public static User findUserBy(UserRepository userRepository, Long userId) {
        User user = userRepository.findUserById(userId);
//        if (user == null) {
//            throw new NotFoundException(String.format("존재하지 않는 유저 %s 입니다", userId));
//        }
        return user;
    }
}
