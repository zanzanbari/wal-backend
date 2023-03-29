package backend.wal.onboarding.domain.entity;

import backend.wal.onboard.domain.onboarding.aggregate.Onboarding;
import backend.wal.onboard.domain.onboarding.aggregate.OnboardingCategory;
import backend.wal.onboard.domain.onboarding.aggregate.OnboardingTime;
import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.common.WalTimeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
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

    @DisplayName("수정한 시간대를 받아 원래 시간대와 비교해 취소된 시간을 추출한다")
    @ParameterizedTest
    @MethodSource("provideModifiedTimeTypesAndExpectCanceledTimeTypes")
    void extractCancelTimeTypes(Set<WalTimeType> modifiedTimeTypes, Set<WalTimeType> expectCanceledTypes) {
        // given
        Onboarding onboarding = Onboarding.newInstance(USER_ID);
        onboarding.addTimes(Set.of(MORNING, AFTERNOON, NIGHT));

        // when
        Set<WalTimeType> canceledTimeTypes = onboarding.extractCancelTimeTypes(modifiedTimeTypes);

        // then
        assertThat(canceledTimeTypes).isEqualTo(expectCanceledTypes);
    }

    private static Stream<Arguments> provideModifiedTimeTypesAndExpectCanceledTimeTypes() {
        return Stream.of(
                Arguments.of(Set.of(MORNING, AFTERNOON), Set.of(NIGHT)),
                Arguments.of(Set.of(MORNING), Set.of(AFTERNOON, NIGHT))
        );
    }

    @DisplayName("수정한 시간대를 받아 원래 시간대와 비교해 취소된 시간대를 삭제하고 남은 시간대를 반환한다")
    @ParameterizedTest
    @MethodSource("provideModifiedTimeTypesAndExpectRemainTimeTypes")
    void removeCanceledTimeTypes(Set<WalTimeType> modifiedTimeTypes, Set<WalTimeType> expectRemainTypes) {
        // given
        Onboarding onboarding = Onboarding.newInstance(USER_ID);
        onboarding.addTimes(Set.of(MORNING, AFTERNOON, NIGHT));

        // when
        Set<WalTimeType> remainTimeTypes = onboarding.removeCanceledTimeTypes(modifiedTimeTypes);

        // then
        assertThat(remainTimeTypes).isEqualTo(expectRemainTypes);
    }

    private static Stream<Arguments> provideModifiedTimeTypesAndExpectRemainTimeTypes() {
        return Stream.of(
                Arguments.of(Set.of(MORNING, AFTERNOON), Set.of(MORNING, AFTERNOON)),
                Arguments.of(Set.of(MORNING), Set.of(MORNING))
        );
    }

    @DisplayName("수정한 시간대와 취소되고 남은 시간대를 받아 비교를 통해 실제로 추가해야하는 시간대를 추출한다")
    @ParameterizedTest
    @MethodSource("provideModifiedTimeTypesAndExpectAddTimeTypesAndExpectSize")
    void extractAddTimeTypes(Set<WalTimeType> modifiedTimeTypes, Set<WalTimeType> expectAddTypes, int expectSize) {
        // given
        Onboarding onboarding = Onboarding.newInstance(USER_ID);
        onboarding.addTimes(Set.of(MORNING));
        Set<WalTimeType> remainTimeTypes = onboarding.removeCanceledTimeTypes(modifiedTimeTypes);

        // when
        Set<WalTimeType> willAddTypes = onboarding.extractAddTimeTypes(modifiedTimeTypes, remainTimeTypes);

        // then
        assertAll(
                () -> assertThat(willAddTypes).hasSize(expectSize),
                ()-> assertThat(willAddTypes).isEqualTo(expectAddTypes)
        );
    }

    private static Stream<Arguments> provideModifiedTimeTypesAndExpectAddTimeTypesAndExpectSize() {
        return Stream.of(
                Arguments.of(Set.of(MORNING, AFTERNOON, NIGHT), Set.of(AFTERNOON, NIGHT), 2),
                Arguments.of(Set.of(MORNING, AFTERNOON), Set.of(AFTERNOON), 1),
                Arguments.of(Set.of(AFTERNOON, NIGHT), Set.of(AFTERNOON, NIGHT), 2),
                Arguments.of(Set.of(NIGHT), Set.of(NIGHT), 1),
                Arguments.of(Set.of(MORNING), Set.of(), 0)
        );
    }

    @DisplayName("수정한 카테고리를 받아 원래 카테고리와 비교해 취소된 카테고리 추출한다")
    @ParameterizedTest
    @MethodSource("provideModifiedCategoryTypesAndExpectCanceledCategoryTypes")
    void extractCancelCategoryTypes(Set<WalCategoryType> modifiedCategoryTypes, Set<WalCategoryType> expectCanceledTypes) {
        // given
        Onboarding onboarding = Onboarding.newInstance(USER_ID);
        onboarding.addCategories(Set.of(COMEDY, FUSS, YELL));

        // when
        Set<WalCategoryType> canceledCategoryTypes = onboarding.extractCancelCategoryTypes(modifiedCategoryTypes);

        // then
        assertThat(canceledCategoryTypes).isEqualTo(expectCanceledTypes);
    }

    private static Stream<Arguments> provideModifiedCategoryTypesAndExpectCanceledCategoryTypes() {
        return Stream.of(
                Arguments.of(Set.of(COMEDY, FUSS, COMFORT), Set.of(YELL)),
                Arguments.of(Set.of(COMEDY, FUSS), Set.of(YELL)),
                Arguments.of(Set.of(COMEDY), Set.of(FUSS, YELL)),
                Arguments.of(Set.of(COMFORT), Set.of(COMEDY, FUSS, YELL))
        );
    }

    @DisplayName("수정한 카테고리를 받아 원래 카테고리와 비교해 취소된 카테고리를 삭제하고 남은 카테고리를 반환한다")
    @ParameterizedTest
    @MethodSource("provideModifiedCategoryTypesAndExpectRemainCategoryTypes")
    void removeCanceledCategoryTypes(Set<WalCategoryType> modifiedCategoryTypes, Set<WalCategoryType> expectRemainTypes) {
        // given
        Onboarding onboarding = Onboarding.newInstance(USER_ID);
        onboarding.addCategories(Set.of(COMEDY, FUSS, YELL));

        // when
        Set<WalCategoryType> remainCategoryTypes = onboarding.removeCanceledCategoryTypes(modifiedCategoryTypes);

        // then
        assertThat(remainCategoryTypes).isEqualTo(expectRemainTypes);
    }

    private static Stream<Arguments> provideModifiedCategoryTypesAndExpectRemainCategoryTypes() {
        return Stream.of(
                Arguments.of(Set.of(COMEDY, FUSS, COMFORT), Set.of(COMEDY, FUSS)),
                Arguments.of(Set.of(COMEDY, FUSS), Set.of(COMEDY, FUSS)),
                Arguments.of(Set.of(COMEDY), Set.of(COMEDY)),
                Arguments.of(Set.of(COMFORT), Set.of())
        );
    }

    @DisplayName("수정한 카테고리와 취소되고 남은 카테고리를 받아 비교를 통해 실제로 추가해야하는 카테고리를 추출한다")
    @ParameterizedTest
    @MethodSource("provideModifiedCategoryTypesAndExpectAddCategoryTypesAndExpectSize")
    void extractAddCategoryTypes(Set<WalCategoryType> modifiedCategoryTypes, Set<WalCategoryType> expectAddTypes, int expectSize) {
        // given
        Onboarding onboarding = Onboarding.newInstance(USER_ID);
        onboarding.addCategories(Set.of(COMEDY));
        Set<WalCategoryType> remainCategoryTypes = onboarding.removeCanceledCategoryTypes(modifiedCategoryTypes);

        // when
        Set<WalCategoryType> willAddTypes = onboarding.extractAddCategoryTypes(modifiedCategoryTypes, remainCategoryTypes);

        // then
        assertAll(
                () -> assertThat(willAddTypes).hasSize(expectSize),
                ()-> assertThat(willAddTypes).isEqualTo(expectAddTypes)
        );
    }

    private static Stream<Arguments> provideModifiedCategoryTypesAndExpectAddCategoryTypesAndExpectSize() {
        return Stream.of(
                Arguments.of(Set.of(COMEDY, FUSS, COMFORT, YELL), Set.of(FUSS, COMFORT, YELL), 3),
                Arguments.of(Set.of(FUSS, COMFORT, YELL), Set.of(FUSS, COMFORT, YELL), 3),
                Arguments.of(Set.of(COMEDY, FUSS, COMFORT), Set.of(FUSS, COMFORT), 2),
                Arguments.of(Set.of(COMEDY, FUSS), Set.of(FUSS), 1),
                Arguments.of(Set.of(FUSS), Set.of(FUSS), 1),
                Arguments.of(Set.of(COMEDY), Set.of(), 0)
        );
    }
}