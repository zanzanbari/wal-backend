package backend.wal.user.app.service;

import backend.wal.advice.exception.ConflictException;
import backend.wal.advice.exception.NotFoundException;
import backend.wal.user.domain.entity.SocialType;
import backend.wal.user.domain.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import org.jetbrains.annotations.NotNull;

public class UserServiceUtils {

    private UserServiceUtils() {
        throw new IllegalStateException("Utility class");
    }

    @NotNull
    public static User findUserBy(UserRepository userRepository, Long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("존재하지 않는 유저 %s 입니다", userId));
        }
        return user;
    }

    static void validateNotExistsUser(UserRepository userRepository, String socialId, SocialType socialType) {
        if (userRepository.existsUserBySocialInfoSocialIdAndSocialInfoSocialType(socialId, socialType)) {
            throw new ConflictException(String.format("이미 존재하는 유저 (%s - %s) 입니다", socialId, socialType));
        }
    }
}
