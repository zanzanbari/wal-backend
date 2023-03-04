package backend.wal.user.service;

import backend.wal.user.domain.entity.User;
import backend.wal.user.dto.request.CreateUserDto;
import backend.wal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
