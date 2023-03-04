package backend.wal.wal.service;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.wal.domain.NextWals;
import backend.wal.wal.domain.entity.Category;
import backend.wal.wal.domain.entity.Item;
import backend.wal.wal.domain.entity.TodayWal;
import backend.wal.wal.domain.repository.CategoryRepository;
import backend.wal.wal.domain.repository.ItemRepository;
import backend.wal.wal.domain.repository.TodayWalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static backend.wal.onboarding.domain.entity.WalCategoryType.*;
import static backend.wal.onboarding.domain.entity.WalTimeType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@Transactional
class WalSettingServiceTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private WalSettingService walSettingService;

    @Autowired
    private TodayWalRepository todayWalRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("유저가 선택한 카테고리에 대해 NextWal 을 세팅하면 선택한 카테고리 수 만큼의 NextWal 이 세팅된다")
    @ParameterizedTest
    @MethodSource("provideCategoryTypesAndExpectSize")
    void setNextWals(Set<WalCategoryType> categoryTypes, int expectSize) {
        // when
        NextWals setNextWals = walSettingService.setNextWals(categoryTypes, USER_ID);

        // then
        assertThat(setNextWals.getSize()).isEqualTo(expectSize);
    }

    private static Stream<Arguments> provideCategoryTypesAndExpectSize() {
        return Stream.of(
                Arguments.of(
                        Set.of(COMEDY, FUSS, COMFORT, YELL), 4),
                Arguments.of(
                        Set.of(COMEDY, FUSS, COMFORT), 3),
                Arguments.of(
                        Set.of(COMEDY, FUSS), 2),
                Arguments.of(
                        Set.of(COMEDY), 1)
        );
    }

    @DisplayName("유저가 선택한 시간대에 대해 TodayWal 을 세팅한다")
    @ParameterizedTest
    @MethodSource("provideTimeTypesAndExpectSize")
    void setTodayWals(Set<WalTimeType> timeTypes, int expectSize) {
        // given
        Category comedy = categoryRepository.findByCategoryType(COMEDY);
        Category fuss = categoryRepository.findByCategoryType(FUSS);
        Category comfort = categoryRepository.findByCategoryType(COMFORT);
        Category yell = categoryRepository.findByCategoryType(YELL);

        Item comedyItem = Item.builder().category(comedy).contents("").imageUrl("").currentItemSize(0).build();
        Item fussItem = Item.builder().category(fuss).contents("").imageUrl("").currentItemSize(0).build();
        Item comfortItem = Item.builder().category(comfort).contents("").imageUrl("").currentItemSize(0).build();
        Item yellItem = Item.builder().category(yell).contents("").imageUrl("").currentItemSize(0).build();

        itemRepository.saveAll(List.of(comedyItem, fussItem, comfortItem, yellItem));

        Set<WalCategoryType> categoryTypes = Set.of(COMEDY, FUSS, COMFORT, YELL);
        NextWals setNextWals = walSettingService.setNextWals(categoryTypes, USER_ID);

        // when
        walSettingService.setTodayWals(timeTypes, USER_ID, setNextWals);

        // then
        List<TodayWal> todayWals = todayWalRepository.findTodayWalsByUserId(USER_ID);
        assertThat(todayWals).hasSize(expectSize);
    }

    private static Stream<Arguments> provideTimeTypesAndExpectSize() {
        return Stream.of(
                Arguments.of(
                        Set.of(MORNING, AFTERNOON, NIGHT), 3),
                Arguments.of(
                        Set.of(MORNING, AFTERNOON), 2),
                Arguments.of(
                        Set.of(MORNING), 1)
        );
    }

    @DisplayName("수정 이후 취소된 카테고리 타입과 userId 를 받아 TodayWal 에서 삭제해야할 시간대를 반환한다")
    @Test
    void updateWalInfoByCancelCategoryTypes() {
        // given

        // when

        // then
    }

    @DisplayName("비어있는 시간대와 추가해야하는 카테코리 타입이 없어도 updateWalInfoByAddCategoryTypesInEmptyTimeTypes 는 동작한다")
    @Test
    void updateWalInfoByAddCategoryTypesInEmptyTimeTypes() {
        // when, then
        assertThatCode(() ->
                walSettingService
                        .updateWalInfoByAddCategoryTypesInEmptyTimeTypes(new HashSet<>(), new HashSet<>(), USER_ID)
        ).doesNotThrowAnyException();
    }
}