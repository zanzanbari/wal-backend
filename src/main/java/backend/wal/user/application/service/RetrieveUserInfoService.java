package backend.wal.user.application.service;

import backend.wal.user.application.port.in.RetrieveUserInfoUseCase;
import backend.wal.user.application.port.out.CategoryTypeResponseDto;
import backend.wal.user.application.port.out.TimeTypeResponseDto;
import backend.wal.user.application.port.out.OnboardingPort;
import backend.wal.user.domain.aggregate.entity.User;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
@Transactional(readOnly = true)
public class RetrieveUserInfoService implements RetrieveUserInfoUseCase {

    private final UserRepository userRepository;
    private final OnboardingPort onboardingPort;

    public RetrieveUserInfoService(final UserRepository userRepository, final OnboardingPort onboardingPort) {
        this.userRepository = userRepository;
        this.onboardingPort = onboardingPort;
    }

    @Override
    public String retrieveNickname(Long userId) {
        User user = UserServiceUtils.findExistsUserByUserId(userRepository, userId);
        return user.getNickname();
    }

    @Override
    public TimeTypeResponseDto retrieveTimeInfo(Long userId) {
        UserServiceUtils.validateExistsUser(userRepository, userId);
        return onboardingPort.retrieveTimeTypes(userId);
    }

    @Override
    public CategoryTypeResponseDto retrieveCategoryInfo(Long userId) {
        UserServiceUtils.validateExistsUser(userRepository, userId);
        return onboardingPort.retrieveCategoryTypes(userId);
    }
}
