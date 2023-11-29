package backend.wal.wal.nextwal.domain;

import backend.wal.wal.item.adapter.out.persistence.ItemEntity;
import backend.wal.wal.item.domain.Category;
import backend.wal.wal.item.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static backend.wal.wal.common.domain.WalCategoryType.*;
import static org.assertj.core.api.Assertions.assertThat;

class NextWalsTest {

    private static final Long USER_ID = 1L;
    private static final Item COMEDY_ITEM = Item.of(ItemEntity.testBuilder().contents("").categoryItemNumber(1).build(), new Category(COMEDY));
    private static final Item FUSS_ITEM = Item.of(ItemEntity.testBuilder().contents("").categoryItemNumber(1).build(), new Category(FUSS));
    private static final Item COMFORT_ITEM = Item.of(ItemEntity.testBuilder().contents("").categoryItemNumber(1).build(), new Category(COMFORT));
    private static final Item YELL_ITEM = Item.of(ItemEntity.testBuilder().contents("").categoryItemNumber(1).build(), new Category(YELL));
    private static final NextWal COMEDY_NEXT_WAL = new NextWal(1L, USER_ID, COMEDY, COMEDY_ITEM);
    private static final NextWal FUSS_NEXT_WAL = new NextWal(1L, USER_ID, FUSS, FUSS_ITEM);
    private static final NextWal COMFORT_NEXT_WAL = new NextWal(1L, USER_ID, COMFORT, COMFORT_ITEM);
    private static final NextWal YELL_NEXT_WAL = new NextWal(1L, USER_ID, YELL, YELL_ITEM);

    private NextWals nextWals;

    @DisplayName("NextWal 과 랜덤범위 난수 생성기를 받아 NextWal 을 가져온다")
    @ParameterizedTest
    @MethodSource("provideGivenNextWalsAndRandomRangeGeneratorAndExpect")
    void getRandomBy(List<NextWal> givenNextWals, RandomRangeGenerator randomRangeGenerator, NextWal expect) {
        // given
        nextWals = new NextWals(givenNextWals);

        // when
        NextWal actual = nextWals.getRandomBy(randomRangeGenerator);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideGivenNextWalsAndRandomRangeGeneratorAndExpect() {
        return Stream.of(
                Arguments.of(
                        new ArrayList<>(List.of(COMEDY_NEXT_WAL, FUSS_NEXT_WAL, COMFORT_NEXT_WAL, YELL_NEXT_WAL)),
                        (RandomRangeGenerator) ignored -> 0,
                        COMEDY_NEXT_WAL),
                Arguments.of(
                        new ArrayList<>(List.of(COMEDY_NEXT_WAL, FUSS_NEXT_WAL, COMFORT_NEXT_WAL, YELL_NEXT_WAL)),
                        (RandomRangeGenerator) ignored -> 1,
                        FUSS_NEXT_WAL),
                Arguments.of(
                        new ArrayList<>(List.of(COMEDY_NEXT_WAL, FUSS_NEXT_WAL, COMFORT_NEXT_WAL, YELL_NEXT_WAL)),
                        (RandomRangeGenerator) ignored -> 2,
                        COMFORT_NEXT_WAL),
                Arguments.of(
                        new ArrayList<>(List.of(COMEDY_NEXT_WAL, FUSS_NEXT_WAL, COMFORT_NEXT_WAL, YELL_NEXT_WAL)),
                        (RandomRangeGenerator) ignored -> 3,
                        YELL_NEXT_WAL)
        );
    }

    @DisplayName("getRandomBy 로 가져온 NextWal 의 정보를 변경해 업데이트한다")
    @Test
    void updateNextWalInfo() {
        // given
        List<NextWal> given = new ArrayList<>(List.of(COMEDY_NEXT_WAL, FUSS_NEXT_WAL));
        nextWals = new NextWals(given);
        RandomRangeGenerator alwaysReturnFixedIndex = ignored -> 0;
        nextWals.getRandomBy(alwaysReturnFixedIndex);

        // when
        nextWals.updateNextWalInfo(FUSS_NEXT_WAL);

        // then
        NextWal updateNextWal = nextWals.getRandomBy(alwaysReturnFixedIndex);
        assertThat(updateNextWal).isEqualTo(FUSS_NEXT_WAL);
    }

    @DisplayName("다음에 전송할 왈소리 카테고리의 아이템을 얻기 위해 해당 카테고리의 전체 사이즈를 이용해 계산한다")
    @ParameterizedTest
    @MethodSource("provideTargetNextWalAndCategoryMaxSizeAndExpect")
    void calculateNextItemId(NextWal targetNextWal, Long countOfCorrespondCategoryType, double expect) {
        // given
        List<NextWal> given = new ArrayList<>(List.of(COMEDY_NEXT_WAL, FUSS_NEXT_WAL));
        nextWals = new NextWals(given);

        // when
        double actual = nextWals.calculateNextItemId(targetNextWal, countOfCorrespondCategoryType);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideTargetNextWalAndCategoryMaxSizeAndExpect() {
        return Stream.of(
                Arguments.of(COMEDY_NEXT_WAL, 100L, 2),
                Arguments.of(COMEDY_NEXT_WAL, 2L, 2),
                Arguments.of(COMEDY_NEXT_WAL, 1L, 1)
        );
    }
}