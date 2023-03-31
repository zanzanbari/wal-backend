package backend.wal.wal.onboarding.domain.repository;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.common.domain.WalTimeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static backend.wal.wal.common.domain.WalTimeType.*;
import static org.assertj.core.api.Assertions.assertThat;

@JpaRepositoryTestConfig
class OnboardingTimeRepositoryTest {

    @Autowired
    private OnboardingTimeRepository onboardingTimeRepository;

    @Autowired
    private OnboardingRepository onboardingRepository;

    @DisplayName("timeType 을 받아 해당 시간대 설정이 되어있는 유저들의 아이디를 가져온다")
    @ParameterizedTest
    @MethodSource("provideWalTimeTypeAndExpectSize")
    void test(WalTimeType timeType, int expect) {
        // given
        Onboarding firstUserOnboarding = Onboarding.newInstance(1L);
        firstUserOnboarding.addTimes(Set.of(MORNING, AFTERNOON, NIGHT));

        Onboarding secondUserOnboarding = Onboarding.newInstance(2L);
        secondUserOnboarding.addTimes(Set.of(MORNING, AFTERNOON));

        Onboarding thirdUserOnboarding = Onboarding.newInstance(3L);
        thirdUserOnboarding.addTimes(Set.of(MORNING));

        onboardingRepository.saveAll(List.of(firstUserOnboarding, secondUserOnboarding, thirdUserOnboarding));

        // when
        List<Long> actual = onboardingTimeRepository.findUserIdsByTimeType(timeType);

        // then
        assertThat(actual).hasSize(expect);
    }

    private static Stream<Arguments> provideWalTimeTypeAndExpectSize() {
        return Stream.of(
                Arguments.of(MORNING, 3),
                Arguments.of(AFTERNOON, 2),
                Arguments.of(NIGHT, 1)
        );
    }
}