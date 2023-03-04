package backend.wal.onboarding.app.service;

import backend.wal.advice.exception.NotFoundException;
import backend.wal.onboarding.app.dto.request.InitOnboardInfoRequestDto;
import backend.wal.onboarding.app.dto.response.OnboardInfoResponse;
import backend.wal.onboarding.domain.WalTimeTypes;
import backend.wal.onboarding.domain.entity.Onboarding;
import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.onboarding.app.dto.request.ModifyOnboardCategoryRequestDto;
import backend.wal.onboarding.app.dto.request.ModifyOnboardTimeRequestDto;
import backend.wal.onboarding.domain.repository.OnboardingRepository;
import backend.wal.user.domain.repository.UserRepository;
import backend.wal.user.domain.entity.User;
import backend.wal.user.app.service.UserServiceUtils;
import backend.wal.wal.domain.NextWals;
import backend.wal.wal.service.WalSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

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

    @Transactional
    public void updateOnboardTimeInfo(ModifyOnboardTimeRequestDto requestDto, Long userId) {
        Onboarding onboarding = findOnboardingBy(userId);
        Set<WalTimeType> modifiedTimeTypes = requestDto.getTimeTypes();
        Set<WalTimeType> canceledTimeTypes = onboarding.extractCancelTimeTypes(modifiedTimeTypes);
        Set<WalTimeType> remainAfterCancel = onboarding.removeCanceledTimeTypes(modifiedTimeTypes);
        Set<WalTimeType> addedTimeTypes = onboarding.extractAddTimeTypes(modifiedTimeTypes, remainAfterCancel);
        onboarding.addTimes(addedTimeTypes);
        updateWalTimeInfo(userId, canceledTimeTypes, addedTimeTypes);
    }

    private void updateWalTimeInfo(Long userId, Set<WalTimeType> canceledTimeTypes, Set<WalTimeType> addedTimeTypes) {
        WalTimeTypes willCancelAfterNow = WalTimeTypes.createCompareAfterNow(canceledTimeTypes);
        WalTimeTypes willAddAfterNow = WalTimeTypes.createCompareAfterNow(addedTimeTypes);
        if (willCancelAfterNow.isExist()) {
            walSettingService.updateTodayWalByCancelTimeTypes(willCancelAfterNow, userId);
        }
        if (willAddAfterNow.isExist()) {
            walSettingService.updateTodayWalByAddTimeTypes(willAddAfterNow, userId);
        }
    }

    @Transactional
    public void updateOnboardCategoryInfo(ModifyOnboardCategoryRequestDto requestDto, Long userId) {
        Onboarding onboarding = findOnboardingBy(userId);
        Set<WalCategoryType> modifiedCategoryTypes = requestDto.getCategoryTypes();
        Set<WalCategoryType> canceledCategoryTypes = onboarding.extractCancelCategoryTypes(modifiedCategoryTypes);
        Set<WalCategoryType> remainAfterCancel = onboarding.removeCanceledCategoryTypes(modifiedCategoryTypes);
        Set<WalCategoryType> addedCategoryTypes = onboarding.extractAddCategoryTypes(modifiedCategoryTypes, remainAfterCancel);
        onboarding.addCategories(addedCategoryTypes);
        updateWalCategoryInfo(userId, canceledCategoryTypes, addedCategoryTypes);
    }

    private void updateWalCategoryInfo(Long userId, Set<WalCategoryType> canceledCategoryTypes,
                                       Set<WalCategoryType> addedCategoryTypes) {
        Set<WalTimeType> willCancelAfterNow = new HashSet<>();
        if (!canceledCategoryTypes.isEmpty()) {
            willCancelAfterNow = walSettingService.updateWalInfoByCancelCategoryTypes(canceledCategoryTypes, userId);
        }
        if (!addedCategoryTypes.isEmpty()) {
            walSettingService.updateWalInfoByAddCategoryTypesInEmptyTimeTypes(willCancelAfterNow, addedCategoryTypes, userId);
        }
    }

    private Onboarding findOnboardingBy(Long userId) {
        Onboarding onboarding = onboardingRepository.findByUserId(userId);
        if (onboarding == null) {
            throw new NotFoundException(String.format("유저 (%s)의 온보딩 정보가 존재하지 않습니다", userId));
        }
        return onboarding;
    }
}
