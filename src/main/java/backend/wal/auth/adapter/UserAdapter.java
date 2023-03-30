package backend.wal.auth.adapter;

import backend.wal.auth.application.port.out.UserPort;
import backend.wal.user.application.port.in.FindSocialUserUseCase;
import backend.wal.user.application.port.in.RegisterUserUseCase;
import backend.wal.user.application.port.in.CreateUserDto;
import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.entity.User;
import org.springframework.stereotype.Component;

@Component
public final class UserAdapter implements UserPort {

    private final FindSocialUserUseCase findSocialUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public UserAdapter(final FindSocialUserUseCase findSocialUserUseCase, final RegisterUserUseCase registerUserUseCase) {
        this.findSocialUserUseCase = findSocialUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    @Override
    public User findSocialUserCall(String socialId, SocialType socialType) {
        return findSocialUserUseCase.findUserBySocialIdAndSocialType(socialId, socialType);
    }

    @Override
    public Long signupCall(CreateUserDto createUserDto) {
        return registerUserUseCase.signup(createUserDto);
    }
}
