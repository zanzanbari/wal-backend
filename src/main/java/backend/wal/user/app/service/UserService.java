package backend.wal.user.app.service;

import backend.wal.user.domain.ResignUserScheduler;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.app.dto.request.CreateUserDto;
import backend.wal.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long signup(CreateUserDto createDto) {
        return User.createGeneral(createDto)
                .getId();
    }

    @Transactional
    public void changeUserNickname(String newNickname, Long userId) {
        User user = UserServiceUtils.findUserBy(userRepository, userId);
        user.changeNickname(newNickname);
    }

    @Transactional
    public void resign(Long userId) {
        User user = UserServiceUtils.findUserBy(userRepository, userId);
        user.resign();

        ResignUserScheduler resignUserScheduler = new ResignUserScheduler();
        resignUserScheduler.resignAfterDay(() -> {
            userRepository.delete(user);
        });
    }
}
