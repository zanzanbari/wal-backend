package backend.wal.onboarding.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static backend.wal.onboarding.domain.entity.WalCategoryType.*;
import static backend.wal.onboarding.domain.entity.WalTimeType.*;
import static org.assertj.core.api.Assertions.assertThat;

class OnboardingTest {

    private static final Long USER_ID = 1L;

    @DisplayName("카테고리 타입을 온보딩 저장한다")
    @ParameterizedTest
    @MethodSource("provideWalCategoryTypesAndExpectSize")
    void addCategories(Set<WalCategoryType> categoryTypes, int expectSize) {
        // given
        Onboarding onboarding = Onboarding.newInstance(USER_ID);

        // when
        onboarding.addCategories(categoryTypes);

        // then
        List<OnboardingCategory> savedCategories = onboarding.getCategories();
        assertThat(savedCategories).hasSize(expectSize);
    }

    private static Stream<Arguments> provideWalCategoryTypesAndExpectSize() {
        return Stream.of(
                Arguments.of(Set.of(COMEDY, COMFORT, FUSS, YELL), 4),
                Arguments.of(Set.of(COMEDY, COMFORT, FUSS), 3),
                Arguments.of(Set.of(COMEDY, COMFORT), 2),
                Arguments.of(Set.of(COMEDY), 1)
        );
    }

    @DisplayName("알람 시간 타입을 온보딩에 저장한다")
    @ParameterizedTest
    @MethodSource("provideWalTimeTypesAndExpectSize")
    void addTimes(Set<WalTimeType> timeTypes, int expectSize) {
        // given
        Onboarding onboarding = Onboarding.newInstance(USER_ID);

        // when
        onboarding.addTimes(timeTypes);

        // then
        List<OnboardingTime> savedTimes = onboarding.getTimes();
        assertThat(savedTimes).hasSize(expectSize);
    }

    private static Stream<Arguments> provideWalTimeTypesAndExpectSize() {
        return Stream.of(
                Arguments.of(Set.of(MORNING, AFTERNOON, NIGHT), 3),
                Arguments.of(Set.of(MORNING, AFTERNOON), 2),
                Arguments.of(Set.of(MORNING), 1)
        );
    }
}