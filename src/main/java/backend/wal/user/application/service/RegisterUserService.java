package backend.wal.user.application.service;

import backend.wal.user.application.port.in.RegisterUserUseCase;
import backend.wal.user.application.port.in.CreateUserDto;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;

    public RegisterUserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Long signup(CreateUserDto createDto) {
        return userRepository.save(User.createGeneral(createDto))
                .getId();
    }
}
