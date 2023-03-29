package backend.wal.user.application.service;

import backend.wal.user.application.port.FindSocialUserUseCase;
import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.support.annotation.AppService;
import org.springframework.transaction.annotation.Transactional;

@AppService
@Transactional(readOnly = true)
public class FindSocialUserService implements FindSocialUserUseCase {

    private final UserRepository userRepository;

    public FindSocialUserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserBySocialIdAndSocialType(String socialId, SocialType socialType) {
        return userRepository.findUserBySocialInfoSocialIdAndSocialInfoSocialType(socialId, socialType);
    }
}
