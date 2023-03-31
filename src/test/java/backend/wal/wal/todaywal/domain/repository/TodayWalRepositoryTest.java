//package backend.wal.wal.todaywal.domain.repository;
//
//import backend.wal.config.jpa.JPAConfig;
//import backend.wal.wal.common.domain.WalCategoryType;
//import backend.wal.wal.common.domain.WalTimeType;
//import backend.wal.wal.nextwal.domain.NextWals;
//import backend.wal.wal.nextwal.domain.aggregate.Category;
//import backend.wal.wal.nextwal.domain.aggregate.Item;
//import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
//import backend.wal.wal.nextwal.domain.repository.CategoryRepository;
//import backend.wal.wal.nextwal.domain.repository.ItemRepository;
//import backend.wal.wal.onboarding.adapter.WalSettingAdapter;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Stream;
//
//import static backend.wal.wal.common.domain.WalCategoryType.*;
//import static backend.wal.wal.common.domain.WalTimeType.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@Import(JPAConfig.class)
//class TodayWalRepositoryTest {
//
//    private static final Long USER_ID = 1L;
//
//    @Autowired
//    private TodayWalRepository todayWalRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private WalSettingAdapter walSettingAdapter;
//
//    @DisplayName("userId 로 해당 유저의 TodayWal 정보들을 가져온다")
//    @Test
//    void findTodayWalsByUserId() {
//        // given
//        Category comedy = categoryRepository.findByCategoryType(COMEDY);
//        Category fuss = categoryRepository.findByCategoryType(FUSS);
//        Category comfort = categoryRepository.findByCategoryType(COMFORT);
//        Category yell = categoryRepository.findByCategoryType(YELL);
//
//        Item comedyItem = Item.builder().category(comedy).contents("").imageUrl("").currentItemSize(0).build();
//        Item fussItem = Item.builder().category(fuss).contents("").imageUrl("").currentItemSize(0).build();
//        Item comfortItem = Item.builder().category(comfort).contents("").imageUrl("").currentItemSize(0).build();
//        Item yellItem = Item.builder().category(yell).contents("").imageUrl("").currentItemSize(0).build();
//
//        itemRepository.saveAll(List.of(comedyItem, fussItem, comfortItem, yellItem));
//
//        NextWals save = walSettingAdapter.setNextWals(Set.of(COMEDY, FUSS, COMFORT, YELL), USER_ID);
//        walSettingAdapter.setTodayWals(Set.of(MORNING, AFTERNOON, NIGHT), USER_ID, save);
//
//        // when
//        List<TodayWal> find = todayWalRepository.findTodayWalsByUserId(USER_ID);
//
//        // then
//        assertThat(find).hasSize(3);
//    }
//
//    @DisplayName("timeType 들을 받아 해당 시간 타입과 일치하는 TodayWal 들을 가져온다")
//    @ParameterizedTest
//    @MethodSource("provideTimeTypesAndExpectSize")
//    void findTodayWalsByTimeTypeInAndUserId(Set<WalTimeType> timeTypes, int expectSize) {
//        // given
//        Category comedy = categoryRepository.findByCategoryType(COMEDY);
//        Category fuss = categoryRepository.findByCategoryType(FUSS);
//        Category comfort = categoryRepository.findByCategoryType(COMFORT);
//        Category yell = categoryRepository.findByCategoryType(YELL);
//
//        Item comedyItem = Item.builder().category(comedy).contents("").imageUrl("").currentItemSize(0).build();
//        Item fussItem = Item.builder().category(fuss).contents("").imageUrl("").currentItemSize(0).build();
//        Item comfortItem = Item.builder().category(comfort).contents("").imageUrl("").currentItemSize(0).build();
//        Item yellItem = Item.builder().category(yell).contents("").imageUrl("").currentItemSize(0).build();
//
//        itemRepository.saveAll(List.of(comedyItem, fussItem, comfortItem, yellItem));
//
//        NextWals save = walSettingAdapter.setNextWals(Set.of(COMEDY, FUSS, COMFORT, YELL), USER_ID);
//        walSettingAdapter.setTodayWals(Set.of(MORNING, AFTERNOON, NIGHT), USER_ID, save);
//
//        // when
//        List<TodayWal> find = todayWalRepository.findTodayWalsByTimeTypeInAndUserId(timeTypes, USER_ID);
//
//        // then
//        assertThat(find).hasSize(expectSize);
//    }
//
//    private static Stream<Arguments> provideTimeTypesAndExpectSize() {
//        return Stream.of(
//                Arguments.of(Set.of(), 0),
//                Arguments.of(Set.of(MORNING), 1),
//                Arguments.of(Set.of(MORNING, AFTERNOON), 2),
//                Arguments.of(Set.of(MORNING, AFTERNOON, NIGHT), 3)
//        );
//    }
//
//    @DisplayName("categoryType 들을 받아 해당 카테고리 타입과 일치하는 TodayWal 들을 가져온다")
//    @ParameterizedTest
//    @MethodSource("provideCategoryTypesAndExpectSize")
//    void findTodayWalsByCategoryTypeInAndUserId(Set<WalCategoryType> categoryTypes, int expectSize) {
//        // given
//        TodayWal morning = TodayWal.builder().userId(USER_ID).categoryType(COMEDY).timeType(MORNING).message("").build();
//        TodayWal afternoon = TodayWal.builder().userId(USER_ID).categoryType(FUSS).timeType(AFTERNOON).message("").build();
//        TodayWal night = TodayWal.builder().userId(USER_ID).categoryType(COMFORT).timeType(NIGHT).message("").build();
//        todayWalRepository.saveAll(List.of(morning, afternoon, night));
//
//        // when
//        List<TodayWal> find = todayWalRepository.findTodayWalsByCategoryTypeInAndUserId(categoryTypes, USER_ID);
//
//        // then
//        assertThat(find).hasSize(expectSize);
//    }
//
//    private static Stream<Arguments> provideCategoryTypesAndExpectSize() {
//        return Stream.of(
//                Arguments.of(Set.of(), 0),
//                Arguments.of(Set.of(YELL), 0),
//                Arguments.of(Set.of(COMEDY), 1),
//                Arguments.of(Set.of(COMEDY, FUSS), 2),
//                Arguments.of(Set.of(COMEDY, FUSS, COMFORT), 3),
//                Arguments.of(Set.of(COMEDY, FUSS, COMFORT, YELL), 3)
//        );
//    }
//
//    @DisplayName("todayWalId 와 userId 로 해당 TodayWal 을 가져온다")
//    @Test
//    void findTodayWalByIdAndUserId() {
//        // given
//        TodayWal save = todayWalRepository.save(
//                TodayWal.builder().userId(USER_ID).categoryType(COMEDY).timeType(MORNING).message("").build());
//
//        // when
//        TodayWal todayWal = todayWalRepository.findTodayWalByIdAndUserId(save.getId(), USER_ID).orElseThrow();
//
//        // then
//        assertThat(todayWal).isEqualTo(save);
//    }
//}