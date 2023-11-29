//package backend.wal.wal.onboarding.adapter;
//
//import backend.wal.wal.common.domain.WalCategoryType;
//import backend.wal.wal.common.domain.WalTimeType;
//import backend.wal.wal.item.adapter.out.persistence.CategoryEntity;
//import backend.wal.wal.item.adapter.out.persistence.CategoryRepository;
//import backend.wal.wal.item.adapter.out.persistence.ItemEntity;
//import backend.wal.wal.item.adapter.out.persistence.ItemRepository;
//import backend.wal.wal.nextwal.domain.NextWals;
//import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
//import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Stream;
//
//import static backend.wal.wal.common.domain.WalCategoryType.*;
//import static backend.wal.wal.common.domain.WalTimeType.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatCode;
//
//@SpringBootTest
//@Transactional
//class WalSettingAdapterTest {
//
//    private static final Long USER_ID = 1L;
//
//    @Autowired
//    private WalSettingAdapter walSettingAdapter;
//
//    @Autowired
//    private TodayWalRepository todayWalRepository;
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @DisplayName("유저가 선택한 카테고리에 대해 NextWal 을 세팅하면 선택한 카테고리 수 만큼의 NextWal 이 세팅된다")
//    @ParameterizedTest
//    @MethodSource("provideCategoryTypesAndExpectSize")
//    void setNextWals(Set<WalCategoryType> categoryTypes, int expectSize) {
//        // when
//        NextWals setNextWals = walSettingAdapter.setNextWals(categoryTypes, USER_ID);
//
//        // then
//        assertThat(setNextWals.getSize()).isEqualTo(expectSize);
//    }
//
//    private static Stream<Arguments> provideCategoryTypesAndExpectSize() {
//        return Stream.of(
//                Arguments.of(
//                        Set.of(COMEDY, FUSS, COMFORT, YELL), 4),
//                Arguments.of(
//                        Set.of(COMEDY, FUSS, COMFORT), 3),
//                Arguments.of(
//                        Set.of(COMEDY, FUSS), 2),
//                Arguments.of(
//                        Set.of(COMEDY), 1)
//        );
//    }
//
//    @DisplayName("유저가 선택한 시간대에 대해 TodayWal 을 세팅한다")
//    @ParameterizedTest
//    @MethodSource("provideTimeTypesAndExpectSize")
//    void setTodayWals(Set<WalTimeType> timeTypes, int expectSize) {
//        // given
//        CategoryEntity comedy = categoryRepository.findByCategoryType(COMEDY);
//        CategoryEntity fuss = categoryRepository.findByCategoryType(FUSS);
//        CategoryEntity comfort = categoryRepository.findByCategoryType(COMFORT);
//        CategoryEntity yell = categoryRepository.findByCategoryType(YELL);
//
//        ItemEntity comedyItem = ItemEntity.testBuilder().category(comedy).contents("").imageUrl("").categoryItemNumber(1).build();
//        ItemEntity fussItem = ItemEntity.testBuilder().category(fuss).contents("").imageUrl("").categoryItemNumber(1).build();
//        ItemEntity comfortItem = ItemEntity.testBuilder().category(comfort).contents("").imageUrl("").categoryItemNumber(1).build();
//        ItemEntity yellItem = ItemEntity.testBuilder().category(yell).contents("").imageUrl("").categoryItemNumber(1).build();
//
//        itemRepository.saveAll(List.of(comedyItem, fussItem, comfortItem, yellItem));
//
//        Set<WalCategoryType> categoryTypes = Set.of(COMEDY, FUSS, COMFORT, YELL);
//        NextWals setNextWals = walSettingAdapter.setNextWals(categoryTypes, USER_ID);
//
//        // when
//        walSettingAdapter.setTodayWals(timeTypes, USER_ID, setNextWals);
//
//        // then
//        List<TodayWal> todayWals = todayWalRepository.findTodayWalsByUserId(USER_ID);
//        assertThat(todayWals).hasSize(expectSize);
//    }
//
//    private static Stream<Arguments> provideTimeTypesAndExpectSize() {
//        return Stream.of(
//                Arguments.of(
//                        Set.of(MORNING, AFTERNOON, NIGHT), 3),
//                Arguments.of(
//                        Set.of(MORNING, AFTERNOON), 2),
//                Arguments.of(
//                        Set.of(MORNING), 1)
//        );
//    }
//
//    @DisplayName("수정 이후 취소된 카테고리 타입과 userId 를 받아 TodayWal 에서 삭제해야할 시간대를 반환한다")
//    @Test
//    void updateWalInfoByCancelCategoryTypes() {
//        // given
//
//        // when
//
//        // then
//    }
//
//    @DisplayName("비어있는 시간대와 추가해야하는 카테코리 타입이 없어도 updateWalInfoByAddCategoryTypesInEmptyTimeTypes 는 동작한다")
//    @Test
//    void updateWalInfoByAddCategoryTypesInEmptyTimeTypes() {
//        // when, then
//        assertThatCode(() ->
//                walSettingAdapter
//                        .updateWalInfoByAddCategoryTypesInEmptyTimeTypes(new HashSet<>(), new HashSet<>(), USER_ID)
//        ).doesNotThrowAnyException();
//    }
//}