package backend.wal.user.service;

import backend.wal.user.domain.entity.Token;
import backend.wal.user.domain.entity.User;
import backend.wal.user.dto.request.CreateUserDto;
import backend.wal.user.repository.TokenRepository;
import backend.wal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Transactional
    public void registerUser(CreateUserDto createDto) {
        UserServiceUtils.validateNotExistsUser(userRepository, createDto.getSocialId(), createDto.getSocialType());
        User user = User.createGeneral(createDto);
        user.setTokenInfo(tokenRepository.save(Token.newInstance(user.getId(), createDto.getFcmToken())));
    }

    @Transactional
    public void changeUserNickname(String newNickname, Long userId) {
        User user = UserServiceUtils.findUserBy(userRepository, userId);
        user.changeNickname(newNickname);
    }
}
