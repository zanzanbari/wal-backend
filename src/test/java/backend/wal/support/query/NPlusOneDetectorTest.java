package backend.wal.support.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NPlusOneDetectorTest {

    @DisplayName("select 쿼리의 인덱스 값을 가진 리스트의 각 요소가 연속된다면(1씩 차이 난다면) true, 그렇지 않다면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("providerIndexOfSelectQueryAndExpect")
    void detect(List<Integer> indexOfSelectQuery, boolean expect) {
        // given
        NPlusOneDetector nPlusOneDetector = new NPlusOneDetector(indexOfSelectQuery);

        // when
        boolean actual = nPlusOneDetector.detect();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> providerIndexOfSelectQueryAndExpect() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5), true),
                Arguments.of(List.of(1, 2, 3, 4, 6), false),
                Arguments.of(List.of(1, 2, 8, 9, 15, 16, 17, 18, 19), true)
        );
    }

    @DisplayName("select 쿼리의 인덱스 리스트의 개수가 5개 이상이면 true 를, 5개보다 작다면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("providerBoundaryOfWarnCountAndExpect")
    void isSelectCountOverThanWarnCount(List<Integer> indexOfSelectQuery, boolean expect) {
        // given
        NPlusOneDetector nPlusOneDetector = new NPlusOneDetector(indexOfSelectQuery);

        // when
        boolean actual = nPlusOneDetector.isSelectCountOverThanWarnCount();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> providerBoundaryOfWarnCountAndExpect() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4), false),
                Arguments.of(List.of(1, 2, 3, 4, 5), true)
        );
    }
}