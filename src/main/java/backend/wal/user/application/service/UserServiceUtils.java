package backend.wal.user.application.service;

import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.user.exception.NotFoundUserException;

public final class UserServiceUtils {

    private UserServiceUtils() throws InstantiationException {
        throw new InstantiationException("Block Instantiation");
    }

    static User findExistsUserByUserId(UserRepository userRepository, Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> NotFoundUserException.notExists(userId));
    }

    static void validateExistsUser(UserRepository userRepository, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw NotFoundUserException.notExists(userId);
        }
    }
}
