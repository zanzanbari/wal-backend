package backend.wal.wal.nextwal.domain.repository;

import backend.wal.wal.common.TestItemInitializer;
import backend.wal.wal.item.domain.aggregate.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static backend.wal.wal.common.domain.WalCategoryType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ItemRepositoryTest extends TestItemInitializer {

    @DisplayName("주어진 카테고리 타입의 첫번째 아이템을 가져온다")
    @Test
     void findFirstByCategoryType() {
        // given
        setForItemRepositoryTest();

        // when
        List<Item> items = itemRepository.findFirstItemsByCategoryTypes(Set.of(COMEDY, COMFORT, YELL, FUSS));

        // then
        for (Item item : items) {
            switch (item.getCategoryType()) {
                case COMEDY: assertThat(item).isEqualTo(comedyItem1); break;
                case FUSS: assertThat(item).isEqualTo(fussItem1); break;
                case COMFORT: assertThat(item).isEqualTo(comfortItem1); break;
                case YELL: assertThat(item).isEqualTo(yellItem1); break;
            }
        }

    }

    @DisplayName("카테고리 타입을 받아 해당 카테고리 타입의 모든 아이템 개수를 가져온다")
    @Test
    void countAllByCategoryType() {
        // given
        setForItemRepositoryTest();
        Long expectSize = 2L;

        // when
        Long comedyActual = itemRepository.countAllByCategoryCategoryType(COMEDY);
        Long fussActual = itemRepository.countAllByCategoryCategoryType(FUSS);
        Long comfortActual = itemRepository.countAllByCategoryCategoryType(COMFORT);
        Long yellActual = itemRepository.countAllByCategoryCategoryType(YELL);

        // then
        assertAll(
                () -> assertThat(comedyActual).isEqualTo(expectSize),
                () -> assertThat(fussActual).isEqualTo(expectSize),
                () -> assertThat(comfortActual).isEqualTo(expectSize),
                () -> assertThat(yellActual).isEqualTo(expectSize)
        );
    }

    @DisplayName("카테고리 타입과 다음 아이템 번호를 받아 해당 카테고리 타입의 다음 아이템을 가져온다")
    @Test
    void findByCategoryTypeAndNextItemId() {
        // given
        setForItemRepositoryTest();

        // when
        Item nextComedyItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(COMEDY, 2);
        Item fussComedyItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(FUSS, 2);
        Item comfortComedyItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(COMFORT, 2);
        Item yellComedyItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(YELL, 2);

        // then
        assertAll(
                () -> assertThat(nextComedyItem).isEqualTo(comedyItem2),
                () -> assertThat(fussComedyItem).isEqualTo(fussItem2),
                () -> assertThat(comfortComedyItem).isEqualTo(comfortItem2),
                () -> assertThat(yellComedyItem).isEqualTo(yellItem2)
        );
    }
}