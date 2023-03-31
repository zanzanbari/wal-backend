//package backend.wal.wal.repository;
//
//import backend.wal.config.JpaRepositoryTestConfig;
//import backend.wal.wal.nextwal.domain.aggregate.Category;
//import backend.wal.wal.nextwal.domain.aggregate.Item;
//import backend.wal.wal.nextwal.domain.repository.CategoryRepository;
//import backend.wal.wal.nextwal.domain.repository.ItemRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@JpaRepositoryTestConfig
//class ItemRepositoryTest {
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    private Item comedyItem1;
//    private Item comedyItem2;
//    private Item fussItem1;
//    private Item fussItem2;
//    private Item comfortItem1;
//    private Item comfortItem2;
//    private Item yellItem1;
//    private Item yellItem2;
//
//    @BeforeEach
//    void setUp() {
//        Category comedy = categoryRepository.findByCategoryType(COMEDY);
//        Category fuss = categoryRepository.findByCategoryType(FUSS);
//        Category comfort = categoryRepository.findByCategoryType(COMFORT);
//        Category yell = categoryRepository.findByCategoryType(YELL);
//
//        comedyItem1 = Item.builder().category(comedy).contents("").imageUrl("").currentItemSize(0).build();
//        comedyItem2 = Item.builder().category(comedy).contents("").imageUrl("").currentItemSize(1).build();
//        comedy.addItem(comedyItem1);
//        comedy.addItem(comedyItem2);
//
//        fussItem1 = Item.builder().category(fuss).contents("").imageUrl("").currentItemSize(0).build();
//        fussItem2 = Item.builder().category(fuss).contents("").imageUrl("").currentItemSize(1).build();
//        fuss.addItem(fussItem1);
//        fuss.addItem(fussItem2);
//
//        comfortItem1 = Item.builder().category(comfort).contents("").imageUrl("").currentItemSize(0).build();
//        comfortItem2 = Item.builder().category(comfort).contents("").imageUrl("").currentItemSize(1).build();
//        comfort.addItem(comfortItem1);
//        comfort.addItem(comfortItem2);
//
//        yellItem1 = Item.builder().category(yell).contents("").imageUrl("").currentItemSize(0).build();
//        yellItem2 = Item.builder().category(yell).contents("").imageUrl("").currentItemSize(1).build();
//        yell.addItem(yellItem1);
//        yell.addItem(yellItem2);
//
//        itemRepository.save(comedyItem1);
//        itemRepository.save(comedyItem2);
//
//        itemRepository.save(fussItem1);
//        itemRepository.save(fussItem2);
//
//        itemRepository.save(comfortItem1);
//        itemRepository.save(comfortItem2);
//
//        itemRepository.save(yellItem1);
//        itemRepository.save(yellItem2);
//    }
//
//    @DisplayName("주어진 카테고리 타입의 첫번째 아이템을 가져온다")
//    @Test
//     void findFirstByCategoryType() {
//        // when
//        Item firstComedyItem = itemRepository.findFirstByCategoryCategoryType(COMEDY);
//
//        // then
//        assertThat(firstComedyItem).isEqualTo(comedyItem1);
//    }
//
//    @DisplayName("카테고리 타입을 받아 해당 카테고리 타입의 모든 아이템 개수를 가져온다")
//    @Test
//    void countAllByCategoryType() {
//        // given
//        Long expectSize = 2L;
//
//        // when
//        Long comedyActual = itemRepository.countAllByCategoryCategoryType(COMEDY);
//        Long fussActual = itemRepository.countAllByCategoryCategoryType(FUSS);
//        Long comfortActual = itemRepository.countAllByCategoryCategoryType(COMFORT);
//        Long yellActual = itemRepository.countAllByCategoryCategoryType(YELL);
//
//        // then
//        assertAll(
//                () -> assertThat(comedyActual).isEqualTo(expectSize),
//                () -> assertThat(fussActual).isEqualTo(expectSize),
//                () -> assertThat(comfortActual).isEqualTo(expectSize),
//                () -> assertThat(yellActual).isEqualTo(expectSize)
//        );
//    }
//
//    @DisplayName("카테고리 타입과 다음 아이템 번호를 받아 해당 카테고리 타입의 다음 아이템을 가져온다")
//    @Test
//    void findByCategoryTypeAndNextItemId() {
//        // when
//        Item nextComedyItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(COMEDY, 2);
//        Item fussComedyItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(FUSS, 2);
//        Item comfortComedyItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(COMFORT, 2);
//        Item yellComedyItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(YELL, 2);
//
//        // then
//        assertAll(
//                () -> assertThat(nextComedyItem).isEqualTo(comedyItem2),
//                () -> assertThat(fussComedyItem).isEqualTo(fussItem2),
//                () -> assertThat(comfortComedyItem).isEqualTo(comfortItem2),
//                () -> assertThat(yellComedyItem).isEqualTo(yellItem2)
//        );
//    }
//}