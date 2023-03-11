package backend.wal.onboarding.domain.repository;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.onboarding.domain.entity.Onboarding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static backend.wal.onboarding.domain.entity.WalCategoryType.*;
import static backend.wal.onboarding.domain.entity.WalTimeType.*;
import static org.assertj.core.api.Assertions.assertThat;

@JpaRepositoryTestConfig
class OnboardingRepositoryTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private OnboardingRepository onboardingRepository;

    private Onboarding onboarding;

    @BeforeEach
    void setUp() {
        onboarding = Onboarding.newInstance(USER_ID);
        onboarding.addCategories(Set.of(COMEDY, FUSS, COMFORT, YELL));
        onboarding.addTimes(Set.of(MORNING, AFTERNOON, NIGHT));
        onboardingRepository.save(onboarding);
    }

    @DisplayName("userId 로 해당 유저의 온보딩 정보를 가져온다")
    @Test
    void findByUserId() {
        // when
        Onboarding find = onboardingRepository.findByUserId(USER_ID);

        // then
        assertThat(find).isEqualTo(onboarding);
    }
}