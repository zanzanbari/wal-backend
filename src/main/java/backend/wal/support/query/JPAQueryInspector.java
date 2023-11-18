package backend.wal.support.query;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JPAQueryInspector implements StatementInspector {

    private static final Logger LOGGER = LoggerFactory.getLogger(JPAQueryInspector.class);
    private static final int MAX_QUERY_COUNT = 10;

    private static final ThreadLocal<QueryManager> queryManagers = new ThreadLocal<>();

    void start() {
        queryManagers.set(new QueryManager(new ArrayList<>()));
    }

    void finish() {
        queryManagers.remove();
    }

    @Override
    public String inspect(String sql) {
        LOGGER.info("sql: {}", sql);
        QueryManager queryManager = queryManagers.get();
        if (queryManager != null) {
            queryManager.addQuery(sql);
        }
        return sql;
    }

    public QueryInspectResult inspectResult() {
        QueryManager queryManager = queryManagers.get();
        int queryCount = queryManager.getQueryCount();
        if (queryManager.hasDuplicatedQuery()) {
            LOGGER.warn("중복된 쿼리가 있습니다");
        }
        if (queryCount > MAX_QUERY_COUNT) {
            LOGGER.warn("쿼리가 10번 이상 실행되었습니다");
        }
        return new QueryInspectResult(queryCount, queryManager.calculateDuration(System.currentTimeMillis()));
    }

    static class QueryInspectResult {

        private final int count;
        private final long time;

        public QueryInspectResult(int count, long time) {
            this.count = count;
            this.time = time;
        }

        public int getCount() {
            return count;
        }

        public long getTime() {
            return time;
        }
    }
}
