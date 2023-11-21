package backend.wal.support.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class QueryManagerTest {

    @DisplayName("query 리스트가 주어지면 select 쿼리의 인덱스를 추출한 리스트를 반환한다")
    @Test
    void extractIndexOfSelectQuery() {
        // given
        List<String> queries = List.of("select", "insert", "update", "delete", "select");
        QueryManager queryManager = new QueryManager(queries, 0);

        // when
        List<Integer> indexOfSelectQuery = queryManager.extractIndexOfSelectQuery();

        // then
        List<Integer> expectIndexOfSelectQuery = List.of(0, 4);
        assertThat(indexOfSelectQuery).isEqualTo(expectIndexOfSelectQuery);
    }

    @DisplayName("쿼리 리스트를 받아, 최대 쿼리 수 10회 이상이면 true 를 반환하고 10회 미만이면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("providerQueriesAndExpect")
    void isOverThanMaxQueryCount(List<String> queries, boolean expect) {
        // given
        QueryManager queryManager = new QueryManager(queries, 0);

        // when
        boolean actual = queryManager.isOverThanMaxQueryCount();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> providerQueriesAndExpect() {
        return Stream.of(
                Arguments.of(List.of(
                        "select", "insert", "update", "delete"),
                        false
                ),
                Arguments.of(List.of(
                        "select", "insert", "update", "delete",
                        "select", "insert", "update", "delete",
                        "select", "insert"),
                        true
                )
        );
    }

    @DisplayName("쿼리 실행 종료 시간을 받아, 쿼리가 실행된 총 시간을 계산한다")
    @Test
    void calculateDuration() {
        // given
        long startTime = 0;
        long endTime = 100;
        QueryManager queryManager = new QueryManager(new ArrayList<>(), startTime);

        // when
        long duration = queryManager.calculateDuration(endTime);

        // then
        assertThat(duration).isEqualTo(endTime - startTime);
    }
}