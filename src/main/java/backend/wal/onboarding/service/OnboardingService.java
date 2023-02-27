package backend.wal.onboarding.service;

import backend.wal.onboarding.domain.entity.Onboarding;
import backend.wal.onboarding.dto.request.InitOnboardInfoRequestDto;
import backend.wal.onboarding.dto.response.OnboardInfoResponse;
import backend.wal.onboarding.repository.OnboardingRepository;
import backend.wal.user.repository.UserRepository;
import backend.wal.user.domain.entity.User;
import backend.wal.user.service.UserServiceUtils;
import backend.wal.wal.domain.NextWals;
import backend.wal.wal.service.WalSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final WalSettingService walSettingService;
    private final UserRepository userRepository;
    private final OnboardingRepository onboardingRepository;

    @Transactional
    public OnboardInfoResponse setOnboardInfo(InitOnboardInfoRequestDto requestDto, Long userId) {
        User user = UserServiceUtils.findUserBy(userRepository, userId);
        user.changeNickname(requestDto.getNickname());

        Onboarding onboarding = onboardingRepository.save(requestDto.toOnboardingEntity(userId));
        user.setOnboardInfo(onboarding);

        NextWals setNextWals = walSettingService.setNextWals(requestDto.getCategoryTypes(), userId);
        walSettingService.setTodayWals(requestDto.getTimeTypes(), userId, setNextWals);

        return new OnboardInfoResponse(user.getNickname());
    }
}
